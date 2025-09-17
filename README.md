# NEAR JSON-RPC Kotlin Client


A Kotlin Multiplatform client for the NEAR JSON-RPC API. This project is auto-generated from the official [nearcore `openapi.json` spec](https://github.com/near/nearcore/blob/master/chain/jsonrpc/openapi/openapi.json).

## Project Status

**The code generator is now fully functional with JsonRpcTransport integration.** It correctly produces a type-safe Kotlin client for all RPC methods defined in the OpenAPI specification.

**Completed Milestones:**
- ✅ **Project Setup:** Configured a multi-module Gradle project recognized by Android Studio/IntelliJ IDEA.
- ✅ **Type Generation (`types` module):** The generator successfully creates serializable Kotlin `data class` models for all RPC schemas.
- ✅ **Client Generation (`client` module):** The generator now creates a complete, type-safe `NearRpcClient` with functions for all RPC methods, correctly handling those with and without parameters.
- ✅ **JsonRpcTransport Integration:** Added `JsonRpcTransport` class for proper JSON-RPC envelope handling with snake_case method names.
- ✅ **Buildable & Verified:** The entire project, including the generated `types` and `client` modules, is fully buildable.
- ✅ **Housekeeping:** The project includes a comprehensive `.gitignore` and an Apache-2.0 `LICENSE`.

**Next Steps:**
- 🚧 **Implement Unit & Integration Tests:** Create test suites to verify the correctness of serialization and client functions.
- 🚧 **Setup CI/CD:** Add GitHub Actions to automate code generation, building, and testing.
- 🚧 **Publish Packages:** Prepare the library for publishing to a public repository like Maven Central.

## Usage

Here is a basic example of how to use the generated client in your Kotlin project.

1.  **Add Dependencies:**
    Make sure you have a dependency on the `client` module in your `build.gradle.kts`.

2.  **Instantiate and Use the Client:**

    ```kotlin
    import com.near.jsonrpc.client.NearRpcClient
    import com.near.jsonrpc.JsonRpcTransport
    import com.near.jsonrpc.types.BlockReference
    import com.near.jsonrpc.types.Finality
    import io.ktor.client.*
    import io.ktor.client.engine.cio.*
    import io.ktor.client.plugins.contentnegotiation.*
    import io.ktor.serialization.kotlinx.json.*
    import kotlinx.coroutines.runBlocking
    import kotlinx.serialization.json.Json

    fun main() = runBlocking {
        // 1. Configure a Ktor HttpClient
        val ktorClient = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                })
            }
        }

        // 2. Create JsonRpcTransport instance
        val transport = JsonRpcTransport(
            client = ktorClient,
            rpcUrl = "https://rpc.mainnet.near.org"
        )

        // 3. Instantiate the NearRpcClient with transport
        val nearClient = NearRpcClient(transport)

        try {
            // 4. Call an RPC method without parameters
            println("Fetching network status...")
            val status = nearClient.status()
            println("Chain ID: \${status.chainId}")
            println("Latest Block Height: \${status.syncInfo.latestBlockHeight}")

            println("\\n--------------------\\n")

            // 5. Call an RPC method with parameters
            println("Fetching latest block...")
            val blockRequest = BlockReference(finality = Finality.FINAL)
            val block = nearClient.block(params = blockRequest)
            println("Block Hash: \${block.header.hash}")
            println("Author: \${block.author}")

        } catch (e: Exception) {
            println("An error occurred: \${e.message}")
            e.printStackTrace()
        } finally {
            // 6. Close the client
            ktorClient.close()
        }
    }
    ```

## Architecture

### JsonRpcTransport Layer

The client uses a dedicated `JsonRpcTransport` class that handles the JSON-RPC protocol specifics:

- **Envelope Creation:** Automatically wraps requests in proper JSON-RPC 2.0 format with `jsonrpc`, `id`, `method`, and `params` fields
- **Snake Case Methods:** RPC method names are converted to snake_case as required by the NEAR protocol
- **Error Handling:** Properly handles JSON-RPC error responses and converts them to exceptions
- **Type Safety:** Uses Kotlin's reified generics for compile-time type checking

### Key Components

- **`JsonRpcTransport`**: Core transport class handling HTTP communication and JSON-RPC protocol
- **`JsonRpcModels`**: Contains error models and shared data structures
- **`NearRpcClient`**: Generated client class with type-safe methods for all RPC endpoints

## Development & Contribution

### Prerequisites
- JDK (Java Development Kit) 17 or higher
- Node.js v18 or higher
- Android Studio or IntelliJ IDEA (Recommended)

### Regenerating Code
The Kotlin source code is not meant to be edited manually. To re-generate all `types` and `client` code after making changes to the generator:

1.  Navigate to the `/generator` directory.
2.  Run `npm install` if you haven't already.
3.  Run the generator script:
    ```bash
    npm run generate
    ```

### Building the Project
1.  Open the **root folder** of this project in Android Studio or IntelliJ IDEA.
2.  Allow the IDE to complete the **Gradle Sync**.
3.  To build the entire project, use the Gradle panel or run the following command in the IDE's terminal:

    - **On Windows (PowerShell):**
      ```powershell
      cmd /c ".\gradlew.bat build"
      ```
    - **On Linux/macOS:**
      ```bash
      ./gradlew build
      ```

## License

This project is licensed under the Apache License, Version 2.0. See the [LICENSE](LICENSE) file for details.