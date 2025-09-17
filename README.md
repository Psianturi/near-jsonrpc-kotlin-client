# NEAR JSON-RPC Kotlin Client


A Kotlin Multiplatform client for the NEAR JSON-RPC API. This project is auto-generated from the official [nearcore `openapi.json` spec](https://github.com/near/nearcore/blob/master/chain/jsonrpc/openapi/openapi.json).

## Project Status

**JsonRpcTransport is now live!** 🚀 Successfully implemented a robust transport layer for NEAR JSON-RPC communication. The core functionality is working great on JVM, with comprehensive unit tests in place.

**What's Working:**
- ✅ **JsonRpcTransport Core:** Complete implementation with JSON-RPC 2.0 support, snake_case methods, and type-safe generics
- ✅ **Unit Tests:** Mock-based tests covering success and error scenarios
- ✅ **JVM Build:** Clean compilation and JAR creation
- ✅ **Generator Integration:** Modified to use the new transport layer
- ✅ **Documentation:** Updated with real examples and architecture details

**Current Setup:**
- 🎯 **JVM Target:** Fully functional and tested
- ⚠️ **JS Targets:** Temporarily disabled to avoid Node.js dependency issues (can be re-enabled later)
- 🎯 **Generator:** Ready to produce JsonRpcTransport-based client code

**Next Steps:**
- Generate the complete NearRpcClient using the updated generator
- Add integration examples with real NEAR RPC calls
- Set up GitHub Actions for automated testing
- Re-enable JS targets when needed

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

### Implementation Details

**JsonRpcTransport Class:**
```kotlin
class JsonRpcTransport(
    val client: HttpClient,                                    // Ktor HTTP client
    val rpcUrl: String,                                        // NEAR RPC endpoint URL
    val json: Json = Json { ignoreUnknownKeys = true }        // JSON serializer
) {
    suspend inline fun <reified P, reified R> call(
        method: String,    // RPC method name (snake_case)
        params: P? = null  // Method parameters (nullable)
    ): R { ... }           // Return typed result
}
```

**Generated Client Pattern:**
```kotlin
class NearRpcClient(private val transport: JsonRpcTransport) {
    // Generated methods follow this pattern:
    suspend fun someRpcMethod(params: SomeParams?): SomeResult {
        return transport.call<SomeParams?, SomeResult>("some_rpc_method", params)
    }
}
```

### Key Components

- **`JsonRpcTransport`**: Core transport class handling HTTP communication and JSON-RPC protocol
- **`JsonRpcModels`**: Contains error models and shared data structures
- **`NearRpcClient`**: Generated client class with type-safe methods for all RPC endpoints
- **`JsonRpcTransportTest`**: Unit tests using Ktor MockEngine for HTTP request/response testing

### Testing

Added solid unit tests for the JsonRpcTransport using Ktor's MockEngine. This lets you test the HTTP layer without hitting real NEAR nodes:

```kotlin
@Test
fun testSuccessfulCall() = runTest {
    val mockResponse = """{"jsonrpc": "2.0", "result": {"bar": "baz"}, "id": "1"}"""
    val mockEngine = MockEngine { respond(mockResponse, HttpStatusCode.OK) }

    val transport = JsonRpcTransport(HttpClient(mockEngine), "https://rpc.testnet.near.org")
    val result: DummyResult = transport.call("dummy_method", DummyParams("hello"))

    assertEquals("baz", result.bar)
}
```

**Run the tests:**
```bash
./gradlew :packages:client:jvmTest  # Just JVM tests (recommended)
```

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