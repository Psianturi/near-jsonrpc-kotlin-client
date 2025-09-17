# NEAR JSON-RPC Kotlin Client

A type-safe Kotlin Multiplatform client for interacting with NEAR blockchain through its JSON-RPC API. This project automatically generates Kotlin code from the official [NEAR core OpenAPI specification](https://github.com/near/nearcore/blob/master/chain/jsonrpc/openapi/openapi.json).

## Features

- **Type-Safe API**: Strongly typed methods with compile-time safety
- **Multiplatform Support**: Works on JVM, with JS support available
- **Auto-Generated**: Code generation from official NEAR OpenAPI spec ensures accuracy
- **Robust Transport Layer**: Custom JsonRpcTransport handles all protocol details
- **Comprehensive Testing**: Unit tests and integration tests included
- **Production Ready**: Clean builds and JAR distribution support

## Supported Platforms

- **JVM**: Fully supported and tested
- **JavaScript/Node.js**: Available (currently disabled due to dependency conflicts)
- **Android**: Compatible through JVM target
- **Kotlin Multiplatform**: Ready for additional targets

## Quick Start

Add the dependency to your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.near:jsonrpc-kotlin-client:1.0.0")
}
```

### Basic Usage

```kotlin
import com.near.jsonrpc.client.NearRpcClient
import com.near.jsonrpc.JsonRpcTransport
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

fun main() = runBlocking {
    // Set up HTTP client
    val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }
    }

    // Create transport layer
    val transport = JsonRpcTransport(
        client = httpClient,
        rpcUrl = "https://rpc.mainnet.near.org"
    )

    // Initialize client
    val client = NearRpcClient(transport)

    try {
        // Get network status
        val status = client.status()
        println("Chain ID: ${status.chainId}")
        println("Latest Block: ${status.syncInfo.latestBlockHeight}")

        // Get latest block
        val block = client.block(mapOf("finality" to "final"))
        println("Block Hash: ${block.header.hash}")

    } catch (e: Exception) {
        println("Error: ${e.message}")
    } finally {
        httpClient.close()
    }
}
```

## Architecture

### Core Components

The library consists of several key components working together:

**JsonRpcTransport** - The foundation layer that handles all JSON-RPC protocol details:
- Automatic JSON-RPC 2.0 envelope creation
- Snake_case method name conversion for NEAR protocol compliance
- Comprehensive error handling with proper exception conversion
- Type-safe generic methods using Kotlin's reified generics

**NearRpcClient** - The main client interface with auto-generated methods:
- Type-safe method signatures for all NEAR RPC endpoints
- Consistent parameter and return types
- Clean, idiomatic Kotlin API

**Generated Types** - Complete type definitions from NEAR's OpenAPI specification:
- 100+ data classes covering all NEAR protocol structures
- Proper serialization annotations for kotlinx.serialization
- Null safety with optional fields where appropriate

### Usage Patterns

```kotlin
// Transport layer handles protocol details
class JsonRpcTransport(
    val client: HttpClient,
    val rpcUrl: String,
    val json: Json = Json { ignoreUnknownKeys = true }
) {
    suspend inline fun <reified P, reified R> call(
        method: String,    // RPC method name
        params: P? = null  // Method parameters
    ): R { /* Implementation */ }
}

// Generated client provides clean API
class NearRpcClient(private val transport: JsonRpcTransport) {
    suspend fun status(): RpcStatusResponse =
        transport.call("status", null)

    suspend fun block(params: BlockReference): RpcBlockResponse =
        transport.call("block", params)
}
```

## Testing

The library includes comprehensive testing support:

### Unit Tests

Test the transport layer without external dependencies using Ktor's MockEngine:

```kotlin
@Test
fun testSuccessfulRpcCall() = runTest {
    val mockResponse = """{"jsonrpc": "2.0", "result": {"status": "ok"}, "id": "1"}"""
    val mockEngine = MockEngine { respond(mockResponse, HttpStatusCode.OK) }

    val transport = JsonRpcTransport(HttpClient(mockEngine), "https://rpc.testnet.near.org")
    val result: NetworkStatus = transport.call("status", null)

    assertEquals("ok", result.status)
}
```

### Integration Tests

Test against real NEAR networks:

```kotlin
@Test
fun testRealNetworkCall() = runBlocking {
    val client = NearRpcClient(JsonRpcTransport(HttpClient(CIO), "https://rpc.testnet.near.org"))
    val status = client.status()

    assertNotNull(status.chainId)
    assertTrue(status.syncInfo.latestBlockHeight > 0)
}
```

### Running Tests

```bash
# Run all tests
./gradlew test

# Run only JVM tests
./gradlew :packages:client:jvmTest

# Run with coverage
./gradlew test jacocoTestReport
```

## Development

### Prerequisites

- JDK 17 or higher
- Node.js 18+ (for code generation)
- IntelliJ IDEA or Android Studio recommended

### Building

```bash
# Clone the repository
git clone <repository-url>
cd near-jsonrpc-kotlin-client

# Build the project
./gradlew build

# Run tests
./gradlew test

# Create JAR
./gradlew jar
```

### Code Generation

The client code generates automatically from NEAR's OpenAPI specification. To regenerate after updates:

```bash
# Navigate to generator directory
cd generator

# Install dependencies
npm install

# Generate code
npm run generate
```

This will update the type definitions and client methods based on the latest NEAR API specification.

### Project Structure

```
├── generator/          # TypeScript code generator
├── packages/
│   ├── client/         # Main client library
│   └── types/          # Generated type definitions
├── gradle/             # Build configuration
└── README.md
```

## API Reference

The client provides type-safe methods for all major NEAR RPC endpoints:

### Network Information
- `status()` - Get network status and version information
- `networkInfo()` - Query current network connections

### Block Operations
- `block(params)` - Get block details by height/hash
- `blockEffects()` - Get block transaction effects

### Account Operations
- `query(params)` - Generic query for accounts, contracts, and state

### Transaction Operations
- `sendTx()` - Send transaction and get execution status
- `tx(params)` - Get transaction details by hash

### Validator Operations
- `validators()` - Get current validator information

### Gas Operations
- `gasPrice(params)` - Get gas price for specific block

All methods return strongly typed responses and accept properly typed parameters for maximum safety and developer experience.

## Contributing

Contributions welcome! Please:

1. Fork the repository
2. Create a feature branch
3. Add tests for new functionality
4. Ensure all tests pass
5. Submit a pull request

## License

Licensed under the Apache License, Version 2.0. See [LICENSE](LICENSE) for details.