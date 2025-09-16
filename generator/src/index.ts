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

    let clientClassContent = `package com.near.jsonrpc.client

import com.near.jsonrpc.types.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

/**
 * A Kotlin Multiplatform JSON-RPC client for the NEAR Protocol.
 *
 * @property client The Ktor HttpClient used for making requests.
 * @property rpcUrl The URL of the NEAR JSON-RPC endpoint.
 */
class NearRpcClient(private val client: HttpClient, private val rpcUrl: String = "https://rpc.mainnet.near.org") {

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

`;

    const paths = spec.paths;
    for (const path in paths) {
        const pathItem = paths[path];
        if (!pathItem.post) {
            continue;
        }

        const operation = pathItem.post;
 
        const rpcMethodName = operation.summary ?? operation.operationId;

        if (!rpcMethodName) {
            console.warn(`Skipping client method for path ${path} due to missing summary/rpcMethodName.`);
            continue;
        }

        
        // Determine return type from response
        if (!operation.responses || !operation.responses["200"]) {
             console.warn(`Skipping client method ${rpcMethodName} due to missing '200' response definition.`);
             continue;
        }
        const responseSchema = operation.responses["200"].content?.["application/json"]?.schema as SchemaObject;
        if (!responseSchema?.properties?.result) {
            console.warn(`Skipping client method ${rpcMethodName} due to missing result schema.`);
            continue;
        }
        const resultSchema = responseSchema.properties.result as SchemaObject;
        const returnType = getKotlinType(resultSchema, spec);
        const functionName = toCamelCase(rpcMethodName);

        if (operation.requestBody && "content" in operation.requestBody) {
            const requestBodySchema = operation.requestBody.content?.["application/json"]?.schema as SchemaObject;
            const requestParams = requestBodySchema?.properties?.params;

            if (requestParams && Object.keys(requestParams).length > 0) {
                console.log(`Skipping method ${rpcMethodName} for now due to parameters.`);
                continue;
            }
        }
        
        clientClassContent += `
    /**
     * Corresponds to the \`${rpcMethodName}\` RPC method.
     * ${operation.description ? `\n     * ${operation.description}`: ''}
     */
    suspend fun ${functionName}(): ${returnType} {
        val requestBody = buildJsonObject {
            put("jsonrpc", "2.0")
            put("id", "dontcare")
            put("method", "${rpcMethodName}")
            put("params", buildJsonObject {})
        }

        val response: JsonObject = client.post(rpcUrl) {
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }.body()

        // Assuming successful response for now, error handling to be added.
        return json.decodeFromJsonElement(response["result"]!!)
    }
`
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