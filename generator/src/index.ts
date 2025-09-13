import SwaggerParser = require("@apidevtools/swagger-parser");
import { OpenAPIObject, SchemaObject, ReferenceObject } from "openapi3-ts/oas31";
import * as fs from "fs-extra";
import * as path from "path";

const RPC_SPEC_URL = "https://raw.githubusercontent.com/near/nearcore/master/chain/jsonrpc/openapi/openapi.json";
const OUTPUT_DIR = path.resolve(__dirname, "../../../packages/types/src/commonMain/kotlin/com/near/jsonrpc/types");

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

async function main() {
    console.log("Fetching and parsing OpenAPI spec...");
    try {
        console.log("Attempting to parse the spec without validation due to schema errors...");
        const spec = await SwaggerParser.parse(RPC_SPEC_URL) as OpenAPIObject;
        console.log("Spec parsed successfully (validation skipped).");

        if (!spec.components?.schemas) {
            console.error("No schemas found in the spec components.");
            return;
        }
        
        await fs.ensureDir(OUTPUT_DIR);

        const schemas = spec.components.schemas;
        for (const schemaName in schemas) {
            const schema = schemas[schemaName] as SchemaObject;
            
            // Do not generate for JsonRpc* requests/responses and simple type aliases for now
            if (schemaName.startsWith("JsonRpc") || !schema.properties) {
                console.log(`Skipping ${schemaName}`);
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
            
            // Remove trailing comma
            if (Object.keys(properties).length > 0) {
                 classContent = classContent.slice(0, -2) + "\n";
            }

            classContent += `)\n`;

            const filePath = path.join(OUTPUT_DIR, `${schemaName}.kt`);
            await fs.writeFile(filePath, classContent);
            console.log(`Generated ${filePath}`);
        }

        console.log(`\n✅ Generation complete. Found ${Object.keys(schemas).length} total schemas.`);

    } catch (err) {
        console.error("Error during code generation:", err);
    }
}

main();