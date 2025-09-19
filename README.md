# NEAR JSON-RPC Kotlin Client

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.20-blue.svg)](https://kotlinlang.org)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![CI](https://github.com/Psianturi/near-jsonrpc-kotlin-client/actions/workflows/ci.yml/badge.svg)](https://github.com/Psianturi/near-jsonrpc-kotlin-client/actions/workflows/ci.yml)
[![Integration Tests](https://github.com/Psianturi/near-jsonrpc-kotlin-client/actions/workflows/integration.yml/badge.svg)](https://github.com/Psianturi/near-jsonrpc-kotlin-client/actions/workflows/integration.yml)

A fully automated, type-safe Kotlin client for NEAR blockchain JSON-RPC API. This project automatically generates Kotlin code from the official [NEAR core OpenAPI specification](https://github.com/near/nearcore/blob/master/chain/jsonrpc/openapi/openapi.json) to provide developers with a high-quality, native mobile experience.

The library consists of two packages:
- **near-jsonrpc-types** - Generated Kotlin data classes and serialization
- **near-jsonrpc-client** - Type-safe RPC client with all NEAR endpoints

[🐛 Report Bug](https://github.com/Psianturi/near-jsonrpc-kotlin-client/issues) • [💡 Request Feature](https://github.com/Psianturi/near-jsonrpc-kotlin-client/issues) • [🤝 Contributing](https://github.com/Psianturi/near-jsonrpc-kotlin-client/blob/main/CONTRIBUTING.md) • [📋 Changelog](https://github.com/Psianturi/near-jsonrpc-kotlin-client/blob/main/CHANGELOG.md)

## ✨ Features

- **🔄 Fully Automated** - GitHub Actions automatically regenerates code from latest NEAR API
- **🛡️ Type-Safe** - Strongly typed methods with compile-time safety
- **🤖 Auto-Generated** - 248+ Kotlin classes from official NEAR OpenAPI spec
- **📱 Android Ready** - JVM target perfect for Android development
- **🧪 Well Tested** - Unit and integration tests with real NEAR networks
- **📦 Two Packages** - Separate types and client libraries for flexibility

## 📦 Packages

This project provides two Kotlin packages:

### near-jsonrpc-types
Contains 248+ auto-generated Kotlin data classes from NEAR's OpenAPI specification.

```kotlin
dependencies {
    implementation("com.near:jsonrpc-types:1.0.0")
}
```

### near-jsonrpc-client
Full RPC client with type-safe methods for all NEAR endpoints.

```kotlin
dependencies {
    implementation("com.near:jsonrpc-client:1.0.0")
    // Includes types automatically
}
```

## 🚀 Quick Start

### Installation

Add JitPack repository to your `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

Then add the client dependency to your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.Psianturi:near-jsonrpc-kotlin-client:v1.0.0")
}
```

Or use the packages separately:

```kotlin
dependencies {
    implementation("com.github.Psianturi:near-jsonrpc-types:v1.0.0")    // Just types
    implementation("com.github.Psianturi:near-jsonrpc-client:v1.0.0")   // Client + types
}
```

**JitPack coordinates:** `com.github.Psianturi:near-jsonrpc-kotlin-client:v1.0.0`

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
    val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    val transport = JsonRpcTransport(httpClient, "https://rpc.mainnet.near.org")
    val client = NearRpcClient(transport)

    // Get network status
    val status = client.status()
    println("Chain: ${status.chainId}")

    // Get gas price
    val gasPrice = client.gasPrice()
    println("Gas price: ${gasPrice.gasPrice}")

    httpClient.close()
}

> **Note:** For Android app development examples using this library, check out: https://github.com/Psianturi/near-kotlin
```

## 📚 Examples

### Network Status
```kotlin
val status = client.status()
println("Chain: ${status.chainId}")
println("Latest block: ${status.syncInfo.latestBlockHeight}")
```

### Account Query
```kotlin
val account = client.query(RpcQueryRequest(
    requestType = "view_account",
    accountId = "example.near",
    finality = "final"
))
println("Balance: ${account.amount}")
```

### Block Information
```kotlin
val block = client.block(BlockReference(finality = "final"))
println("Hash: ${block.header.hash}")
println("Height: ${block.header.height}")
```

### Gas Price
```kotlin
val gasPrice = client.gasPrice(RpcGasPriceRequest())
println("Gas price: ${gasPrice.gasPrice}")
```

### Error Handling
```kotlin
try {
    val tx = client.tx(RpcTransactionStatusRequest(txHash = "abc123..."))
} catch (e: NearRpcException) {
    println("RPC Error: ${e.error.message}")
}
```

## 🏗️ Architecture

The library consists of three main components:

- **JsonRpcTransport** - Handles JSON-RPC protocol details and HTTP communication
- **NearRpcClient** - Type-safe client with auto-generated methods for all NEAR RPC endpoints
- **Generated Types** - 248+ Kotlin data classes from NEAR's OpenAPI specification

## 🛠️ Development

### Prerequisites
- JDK 17 or higher
- Kotlin 1.9.20+
- Node.js 18+ (for code generation)

### Building & Testing

```bash
# Clone the repository
git clone https://github.com/Psianturi/near-jsonrpc-kotlin-client.git
cd near-jsonrpc-kotlin-client

# Build all packages
./gradlew build

# Run unit tests only (fast)
./gradlew :packages:client:jvmTest

# Run integration tests (requires network)
./gradlew :packages:client:jvmTest -Dgroups=integration

# Run all tests
./gradlew test
```

### Code Generation

The client code is automatically generated from NEAR's OpenAPI specification:

```bash
# Install generator dependencies
npm install --prefix generator

# Generate Kotlin types and client
npm run generate --prefix generator

# Build generated code
./gradlew :packages:client:compileKotlinJvm
```

### GitHub Actions Automation

This project uses automated CI/CD:

- **Weekly regeneration** - Automatically fetches latest NEAR API and regenerates code
- **PR creation** - Submits generated code changes for review
- **Automated releases** - Uses release-please for version management
- **Multi-JDK testing** - Tests on JDK 17 and 21

## 🤝 Contributing

We welcome contributions! Here's how to get involved:

### Development Workflow

1. **Fork & Clone** the repository
2. **Create feature branch**: `git checkout -b feature/your-feature`
3. **Make changes** and add tests
4. **Run tests**: `./gradlew build`
5. **Submit PR** with clear description

### Code Generation Updates

When NEAR API changes, the code is automatically regenerated:

1. GitHub Actions fetches latest OpenAPI spec weekly
2. Generates updated Kotlin types and client methods
3. Creates PR for review and testing
4. Merges and releases automatically

### Guidelines

- Follow Kotlin coding conventions
- Add unit tests for new functionality
- Update documentation for API changes
- Ensure `./gradlew build` passes

## 📋 API Reference

### Network
- `status()` - Network status and version
- `networkInfo()` - Network connection info

### Blocks
- `block(params)` - Block details by height/hash
- `blockEffects()` - Block transaction effects

### Accounts
- `query(params)` - Account, contract, and state queries

### Transactions
- `sendTx()` - Send transaction
- `tx(params)` - Transaction details by hash

### Validators
- `validators()` - Current validator information

### Gas
- `gasPrice(params)` - Gas price for specific block

All methods return strongly typed responses for maximum safety.

## 📄 License

Licensed under the Apache License, Version 2.0. See [LICENSE](LICENSE) for details.