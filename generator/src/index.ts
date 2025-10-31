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
        classContent += `import kotlinx.serialization.SerialName\n`;
        classContent += `import kotlinx.serialization.Contextual\n\n`;
        classContent += `@Serializable\n`;
        classContent += `data class ${schemaName}(\n`;

        const properties = schema.properties || {};
        const requiredFields = new Set(schema.required || []);

        for (const propName in properties) {
            const propSchema = properties[propName];
            const camelCaseName = toCamelCase(propName);
            let kotlinType = getKotlinType(propSchema, spec);
            let annotations = `@SerialName("${propName}")`;

            // Add @Contextual annotation for Any types
            if (kotlinType === "Any" || kotlinType === "Any?") {
                annotations += "\n    @Contextual";
            }

            if (!requiredFields.has(propName)) {
                kotlinType += "? = null";
            }

            classContent += `    ${annotations}\n`;
            classContent += `    val ${camelCaseName}: ${kotlinType.replace(/Any/g, '@Contextual Any')},\n`;
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

     // Typed endpoint overrides for selected methods
     // Expanding coverage to more endpoints for better type safety
     const typedEndpoints: Record<string, { paramsType?: string; returnType: string; paramName?: string; defaultExpr?: string }> = {
         // Status & Network Info (no params)
         "status": { returnType: "RpcStatusResponse" },
         "validators": { returnType: "RpcValidatorResponse" },
         "network_info": { returnType: "RpcNetworkInfoResponse" },
         "health": { returnType: "JsonElement" },  // Health returns simple OK or error

         // Query & Block Methods (with params)
         "gas_price": { paramsType: "RpcGasPriceRequest?", returnType: "RpcGasPriceResponse", paramName: "request", defaultExpr: "null" },
         "block": { paramsType: "kotlinx.serialization.json.JsonObject", returnType: "RpcBlockResponse", paramName: "request" },
         "chunk": { paramsType: "kotlinx.serialization.json.JsonObject", returnType: "RpcChunkResponse", paramName: "request" },
         "query": { paramsType: "kotlinx.serialization.json.JsonObject", returnType: "RpcQueryResponse", paramName: "request" },

         // Transaction Methods
         "send_tx": { paramsType: "kotlinx.serialization.json.JsonObject", returnType: "JsonElement", paramName: "transaction" },
         "tx": { paramsType: "kotlinx.serialization.json.JsonObject", returnType: "RpcTransactionResponse", paramName: "request" },
         "EXPERIMENTAL_tx_status": { paramsType: "RpcTransactionStatusRequest", returnType: "RpcTransactionResponse", paramName: "request" },

         // Protocol & Config
         "EXPERIMENTAL_protocol_config": { paramsType: "kotlinx.serialization.json.JsonObject?", returnType: "RpcProtocolConfigResponse", paramName: "params", defaultExpr: "null" },
         "client_config": { returnType: "RpcClientConfigResponse" },

         // Light Client
         "next_light_client_block": { paramsType: "RpcLightClientNextBlockRequest", returnType: "RpcLightClientNextBlockResponse", paramName: "request" },
         "EXPERIMENTAL_light_client_proof": { paramsType: "RpcLightClientExecutionProofRequest", returnType: "RpcLightClientExecutionProofResponse", paramName: "request" },

         // Receipt & Changes
         "EXPERIMENTAL_receipt": { paramsType: "RpcReceiptRequest", returnType: "RpcReceiptResponse", paramName: "request" },
         "EXPERIMENTAL_changes_in_block": { paramsType: "kotlinx.serialization.json.JsonObject", returnType: "RpcStateChangesInBlockResponse", paramName: "request" },
         "EXPERIMENTAL_changes": { paramsType: "kotlinx.serialization.json.JsonObject", returnType: "RpcStateChangesInBlockByTypeResponse", paramName: "request" },

         // Validators & Ordering
         "EXPERIMENTAL_validators_ordered": { paramsType: "RpcValidatorsOrderedRequest?", returnType: "JsonElement", paramName: "request", defaultExpr: "null" }
     };
     
     const paths = spec.paths;

     console.log(`\n📊 Typed endpoints configured: ${Object.keys(typedEndpoints).length} methods`);
     console.log(`   Typed: ${Object.keys(typedEndpoints).join(', ')}`);

     // Count total client endpoints from OpenAPI spec
     let totalClientEndpoints = 0;
     for (const path in paths) {
         if (paths[path].post) {
             totalClientEndpoints++;
         }
     }
     console.log(`   Total client endpoints available: ${totalClientEndpoints}`);
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

        // Check if this endpoint requires parameters based on OpenAPI spec
        const requestBody = operation.requestBody as any;
        const hasRequiredParams = requestBody?.required === true ||
            (requestBody?.content?.['application/json']?.schema?.required?.length > 0);

        // Defaults (backward compatible): untyped params/result
        let paramsKotlinType: string | null = "kotlinx.serialization.json.JsonObject";
        let paramsSignature = "";
        let paramsVariable = "buildJsonObject {}";
        let returnType = "JsonElement";

        // Override with typed signatures if configured for this operationId
        const typed = typedEndpoints[rpcMethodName];
        if (typed) {
            // Set typed return
            returnType = typed.returnType;

            if (typed.paramsType && typed.paramName) {
                // Typed request parameter with default expr (nullable)
                paramsKotlinType = typed.paramsType;
                paramsSignature = `${typed.paramName}: ${typed.paramsType} = ${typed.defaultExpr ?? "null"}`;
                paramsVariable = `${typed.paramName}`;
            } else {
                // No params for typed endpoint -> pass null so JSON-RPC envelope contains "params": null
                paramsKotlinType = "kotlin.Nothing?";
                paramsSignature = "";
                paramsVariable = "null";
            }
        } else if (hasRequiredParams) {
            // Auto-detect required parameters from OpenAPI spec
            paramsSignature = `params: kotlinx.serialization.json.JsonObject`;
            paramsVariable = `params`;
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