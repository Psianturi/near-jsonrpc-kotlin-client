import SwaggerParser = require("@apidevtools/swagger-parser");
import { OpenAPIObject, SchemaObject, ReferenceObject } from "openapi3-ts/oas31";
import * as fs from "fs-extra";
import * as path from "path";

const RPC_SPEC_URL = "https://raw.githubusercontent.com/near/nearcore/master/chain/jsonrpc/openapi/openapi.json";
const OUTPUT_DIR_TYPES = path.resolve(__dirname, "../../../packages/types/src/commonMain/kotlin/com/near/jsonrpc/types");
const OUTPUT_DIR_CLIENT = path.resolve(__dirname, "../../../packages/client/src/commonMain/kotlin/com/near/jsonrpc/client");

function toCamelCase(str: string): string {
    return str.replace(/([-_][a-z])/g, (group) =>
        group.toUpperCase().replace("-", "").replace("_", "")
    );
}

function getKotlinType(schema: SchemaObject | ReferenceObject, spec: OpenAPIObject): string {
    if ("$ref" in schema) {
        // Asserting non-null because the 'in' operator confirms its presence.
        const refName = schema.$ref!.split("/").pop()!;
        return refName;
    }

    switch (schema.type) {
        case "string":
            return "String";
        case "integer":
            // Note: OpenAPI doesn't distinguish between Int, Long, etc.
            // Using Long for safety with uint64 and other large integer types.
            return "Long";
        case "number":
            return "Double";
        case "boolean":
            return "Boolean";
        case "array":
            if (schema.items) {
                return `List<${getKotlinType(schema.items, spec)}>`;
            }
            return "List<Any>";
        case "object":
            if (schema.additionalProperties) {
                if (typeof schema.additionalProperties === 'boolean') {
                    return "Map<String, Any>";
                }
                const valueType = getKotlinType(schema.additionalProperties, spec);
                return `Map<String, ${valueType}>`;
            }
            return "Any";
        default:
            return "Any";
    }
}

async function generateTypes(spec: OpenAPIObject) {
    if (!spec.components?.schemas) {
        console.error("No schemas found in the spec components.");
        return;
    }

    await fs.emptyDir(OUTPUT_DIR_TYPES);
    console.log(`Cleaned ${OUTPUT_DIR_TYPES}`);

    const schemas = spec.components.schemas;
    for (const schemaName in schemas) {
        const schema = schemas[schemaName] as SchemaObject;

        if (schemaName.startsWith("JsonRpc") || !schema.properties) {
            console.log(`Skipping type ${schemaName}`);
            continue;
        }

        let classContent = `package com.near.jsonrpc.types\n\n`;
        classContent += `import kotlinx.serialization.Serializable\n`;
        classContent += `import kotlinx.serialization.SerialName\n\n`;
        classContent += `@Serializable\n`;
        classContent += `data class ${schemaName}(\n`;

        const properties = schema.properties || {};
        const requiredFields = new Set(schema.required || []);

        for (const propName in properties) {
            const propSchema = properties[propName];
            const camelCaseName = toCamelCase(propName);
            let kotlinType = getKotlinType(propSchema, spec);

            if (!requiredFields.has(propName)) {
                kotlinType += "? = null";
            }

            classContent += `    @SerialName("${propName}")\n`;
            classContent += `    val ${camelCaseName}: ${kotlinType},\n`;
        }


        if (Object.keys(properties).length > 0) {
            classContent = classContent.slice(0, -2) + "\n";
        }

        classContent += `)\n`;

        const filePath = path.join(OUTPUT_DIR_TYPES, `${schemaName}.kt`);
        await fs.writeFile(filePath, classContent);
        console.log(`Generated type ${filePath}`);
    }
    console.log(`\n✅ Type generation complete. Found ${Object.keys(schemas).length} total schemas.`);
}

async function generateClient(spec: OpenAPIObject) {
    if (!spec.paths) {
        console.error("No paths found in the spec.");
        return;
    }

    await fs.emptyDir(OUTPUT_DIR_CLIENT);
    console.log(`Cleaned ${OUTPUT_DIR_CLIENT}`);

    // First, generate the base classes needed for all RPC calls
    const baseRpcContent = `package com.near.jsonrpc.client

import com.near.jsonrpc.JsonRpcError

class NearRpcException(message: String, val error: JsonRpcError) : RuntimeException(message)

`
    const baseRpcFilePath = path.join(OUTPUT_DIR_CLIENT, `JsonRpc.kt`);
    await fs.writeFile(baseRpcFilePath, baseRpcContent);
    console.log(`Generated ${baseRpcFilePath}`);


    let clientClassContent = `package com.near.jsonrpc.client

import com.near.jsonrpc.types.*
import com.near.jsonrpc.JsonRpcTransport
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.buildJsonObject

/**
 * A Kotlin Multiplatform JSON-RPC client for the NEAR Protocol.
 *
 * @property transport The JsonRpcTransport used for making requests.
 */
class NearRpcClient(private val transport: JsonRpcTransport) {
`;

    const paths = spec.paths;
    for (const path in paths) {
        const pathItem = paths[path];
        if (!pathItem.post) {
            continue;
        }

        const operation = pathItem.post;
        const rpcMethodName = operation.operationId; // Use operationId as the canonical method name

        if (!rpcMethodName) {
            console.warn(`Skipping path ${path} because it has no operationId.`);
            continue;
        }

        const functionName = toCamelCase(rpcMethodName);

        // --- Determine Parameter Type ---
        let paramsKotlinType: string | null = null;
        let paramsSignature = "";
        let paramsVariable = "kotlinx.serialization.json.buildJsonObject {}"; // Default to empty JSON object

        if (operation.requestBody && "content" in operation.requestBody) {
            const requestSchema = operation.requestBody.content["application/json"].schema as SchemaObject;
            if (requestSchema && "$ref" in requestSchema && requestSchema.$ref) {
                const requestRefName = requestSchema.$ref.split("/").pop()!;
                const fullRequestSchema = spec.components?.schemas?.[requestRefName] as SchemaObject;
                const paramsSchema = fullRequestSchema?.properties?.params as (SchemaObject | ReferenceObject);
                if (paramsSchema) {
                    paramsKotlinType = getKotlinType(paramsSchema, spec);
                    // Only add parameter if a valid type was found
                    if (paramsKotlinType && paramsKotlinType !== "Any" && paramsKotlinType !== "Map<String, Any>") {
                        paramsSignature = `params: ${paramsKotlinType}`;
                        paramsVariable = "params";
                    }
                }
            }
        }

        // Special handling for known RPC methods with parameters
        if (rpcMethodName === "block" && !paramsSignature) {
            paramsSignature = "params: BlockReference";
            paramsVariable = "params";
            paramsKotlinType = "BlockReference";
        } else if (rpcMethodName === "query" && !paramsSignature) {
            paramsSignature = "params: RpcQueryRequest";
            paramsVariable = "params";
            paramsKotlinType = "RpcQueryRequest";
        } else if (rpcMethodName === "gas_price" && !paramsSignature) {
            paramsSignature = "params: RpcGasPriceRequest";
            paramsVariable = "params";
            paramsKotlinType = "RpcGasPriceRequest";
        } else if (rpcMethodName === "tx" && !paramsSignature) {
            paramsSignature = "params: RpcTransactionStatusRequest";
            paramsVariable = "params";
            paramsKotlinType = "RpcTransactionStatusRequest";
        }

        // --- Determine Return Type (Type-Safe) ---
        let returnType = "JsonElement"; // Default fallback
        const responseHolderSchema = operation.responses?.["200"]?.content?.["application/json"].schema as SchemaObject;

        if (responseHolderSchema && "$ref" in responseHolderSchema && responseHolderSchema.$ref) {
            const responseRefName = responseHolderSchema.$ref.split("/").pop()!;
            const fullResponseSchema = spec.components?.schemas?.[responseRefName] as SchemaObject;
            if (fullResponseSchema?.properties?.result) {
                const resultSchema = fullResponseSchema.properties.result as (SchemaObject | ReferenceObject);
                const detectedType = getKotlinType(resultSchema, spec);
                // Use specific types when available, fallback to JsonElement only for complex objects
                if (detectedType !== "Any" && detectedType !== "Map<String, Any>") {
                    returnType = detectedType;
                }
            }
        }

        // Special handling for known RPC methods to use proper response types
        if (rpcMethodName === "status") {
            returnType = "RpcStatusResponse";
        } else if (rpcMethodName === "block") {
            returnType = "RpcBlockResponse";
        } else if (rpcMethodName === "gas_price") {
            returnType = "RpcGasPriceResponse";
        } else if (rpcMethodName === "query") {
            returnType = "RpcQueryResponse";
        } else if (rpcMethodName === "tx") {
            returnType = "RpcTransactionResponse";
        } else if (rpcMethodName === "validators") {
            returnType = "RpcValidatorResponse";
        }

        clientClassContent += `
    /**
      * ${operation.description?.trim().replace(/\n/g, '\n     * ')}
      */
    suspend fun ${functionName}(${paramsSignature}): ${returnType} {
        return transport.call<${paramsKotlinType ?? "kotlinx.serialization.json.JsonObject"}, ${returnType}>("${rpcMethodName}", ${paramsVariable})
    }
`;

        // Add method alias for snake_case methods if different from camelCase
        const snakeCaseMethod = rpcMethodName.replace(/_([a-z])/g, (_, letter) => letter.toUpperCase());
        if (snakeCaseMethod !== functionName && rpcMethodName.includes('_')) {
            clientClassContent += `
    /**
      * ${operation.description?.trim().replace(/\n/g, '\n     * ')}
      * @deprecated Use ${functionName} instead
      */
    @Deprecated("Use ${functionName} instead", ReplaceWith("${functionName}(${paramsVariable})"))
    suspend fun ${snakeCaseMethod}(${paramsSignature}): ${returnType} {
        return ${functionName}(${paramsVariable})
    }
`;
        }
    }

    clientClassContent += `}\n`;

    const filePath = path.join(OUTPUT_DIR_CLIENT, `NearRpcClient.kt`);
    await fs.writeFile(filePath, clientClassContent);
    console.log(`\n✅ Client generation complete. Generated ${filePath}`);
}

async function main() {
    console.log("Fetching and parsing OpenAPI spec...");
    try {
        const spec = await SwaggerParser.dereference(RPC_SPEC_URL) as OpenAPIObject;
        console.log("Spec parsed and dereferenced successfully.");
        
        await fs.ensureDir(OUTPUT_DIR_TYPES);
        await fs.ensureDir(OUTPUT_DIR_CLIENT);

        await generateTypes(spec);
        await generateClient(spec);

    } catch (err) {
        console.error("Error during code generation:", err);
    }
}

main();