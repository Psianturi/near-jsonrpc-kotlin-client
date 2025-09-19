# NEAR JSON-RPC Kotlin Client

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.20-blue.svg)](https://kotlinlang.org)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![CI](https://github.com/Psianturi/near-jsonrpc-kotlin-client/actions/workflows/ci.yml/badge.svg)](https://github.com/Psianturi/near-jsonrpc-kotlin-client/actions/workflows/ci.yml)
[![Integration Tests](https://github.com/Psianturi/near-jsonrpc-kotlin-client/actions/workflows/integration.yml/badge.svg)](https://github.com/Psianturi/near-jsonrpc-kotlin-client/actions/workflows/integration.yml)
[![Code Quality](https://github.com/Psianturi/near-jsonrpc-kotlin-client/actions/workflows/lint.yml/badge.svg)](https://github.com/Psianturi/near-jsonrpc-kotlin-client/actions/workflows/lint.yml)

A fully automated, type-safe Kotlin client for NEAR blockchain JSON-RPC API. This project automatically generates Kotlin code from the official [NEAR core OpenAPI specification](https://github.com/near/nearcore/blob/master/chain/jsonrpc/openapi/openapi.json) to provide developers with a high-quality, native mobile experience.

The library consists of two packages:
- **near-jsonrpc-types** - Generated Kotlin data classes and serialization
- **near-jsonrpc-client** - Type-safe RPC client with all NEAR endpoints

[🐛 Report Bug](https://github.com/Psianturi/near-jsonrpc-kotlin-client/issues) • [💡 Request Feature](https://github.com/Psianturi/near-jsonrpc-kotlin-client/issues) • [🤝 Contributing](https://github.com/Psianturi/near-jsonrpc-kotlin-client/blob/main/CONTRIBUTING.md) • [📋 Changelog](https://github.com/Psianturi/near-jsonrpc-kotlin-client/blob/main/CHANGELOG.md)

## ✨ Features

- **🔄 Fully Automated** - GitHub Actions automatically regenerates code from latest NEAR API
- **🛡️ Type-Safe** - Strongly typed methods with compile-time safety
- **🤖 Auto-Generated** - 248+ Kotlin data classes from official NEAR OpenAPI spec
- **📱 Android Ready** - JVM target perfect for Android development
- **🧪 Well Tested** - Unit and integration tests with real NEAR networks (80%+ coverage)
- **📦 Two Packages** - Separate types and client libraries for flexibility
- **🔄 Auto-Regeneration** - Weekly automated code generation from latest NEAR API

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

### Publishing Status

Currently published via **JitPack** for easy integration. Maven Central / GitHub Packages publication is planned for future releases to provide more formal distribution channels.

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



## 🏗️ Architecture

The library consists of three main components:

- **JsonRpcTransport** - Handles JSON-RPC protocol details and HTTP communication
- **NearRpcClient** - Type-safe client with auto-generated methods for all NEAR RPC endpoints
- **Generated Types** - 248+ Kotlin data classes from NEAR's OpenAPI specification

### Type Safety Coverage

Most endpoints return strongly typed models via the `near-jsonrpc-types` package, but some legacy or less commonly used methods still return `JsonElement` for compatibility. We aim for full type coverage in future releases as the API stabilizes.

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

# Run unit tests only (fast, no network)
./gradlew :packages:client:jvmTest

# Run integration tests (connects to real NEAR testnet)
./gradlew :packages:client:jvmTest -Dgroups=integration

# Run all tests
./gradlew test
```

### 📊 Test Results Example

**Unit Tests Output:**
```bash
> Task :packages:client:jvmTest

com.near.jsonrpc.JsonRpcTransportTest > testSuccessfulCall() PASSED
com.near.jsonrpc.JsonRpcTransportTest > testErrorResponse() PASSED
com.near.jsonrpc.JsonRpcTransportTest > testNullParams() PASSED

BUILD SUCCESSFUL in 8s
3 tests completed, 3 passed
```

**Integration Tests Output:**
```bash
> Task :packages:client:jvmTest

NearRpcClientIntegrationTest > should fetch network status from testnet() PASSED
NearRpcClientIntegrationTest > should fetch block from testnet() PASSED
NearRpcClientIntegrationTest > should fetch gas price from testnet() PASSED

✅ Successfully connected to NEAR testnet!
Status: {"chain_id":"testnet","sync_info":{"latest_block_height":123456789,...}}
Gas Price: {"gas_price":"100000000"}

BUILD SUCCESSFUL in 12s
6 tests completed, 6 passed
```

### 🎯 Expected Behavior

When tests pass, you should see:
- ✅ **Unit tests**: All transport layer tests pass (no network required)
- ✅ **Integration tests**: Successful connection to NEAR testnet with real data
- ✅ **Build verification**: All packages compile and tests pass
- ✅ **Type safety**: All generated types serialize/deserialize correctly

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

### Code Generation Automation

GitHub Actions automatically maintains the library up-to-date with NEAR's API:

- **Weekly Schedule** - Fetches latest NEAR OpenAPI spec every Monday at 09:00 UTC
- **Automated Regeneration** - Generates updated Kotlin types and client methods
- **PR Creation** - Submits generated code changes for human review and testing
- **Continuous Updates** - Ensures library stays current with NEAR protocol changes

### Release Automation

Uses **release-please** for automated versioning and releases:

- **Conventional Commits** - Analyzes commit messages for version bumps
- **Automated PRs** - Creates release PRs with changelogs
- **GitHub Releases** - Automatically creates releases with JAR artifacts
- **Semantic Versioning** - Follows semver for predictable version increments

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

The client provides comprehensive access to all NEAR RPC endpoints with type-safe methods:

### 🔗 **Network & Node Information**
- `status()` - Network status, sync info, validators, and protocol details
- `networkInfo()` - Current network connections and peer information
- `clientConfig()` - Node configuration and settings
- `health()` - Node health status check

### 📦 **Block Operations**
- `block()` - Block details by height/hash/finality
- `blockEffects()` - Changes in block across all transactions
- `chunk()` - Specific chunk details within a block

### 👤 **Account & State Queries**
- `query()` - Generic queries for accounts, contracts, access keys, and state
- `changes()` - Account/contract changes for specific blocks

### 💸 **Transaction Operations**
- `sendTx()` - Send transaction and get execution status
- `tx()` - Transaction details by hash
- `broadcastTxAsync()` - Send transaction (async, deprecated)
- `broadcastTxCommit()` - Send transaction (sync, deprecated)

### ⚡ **Gas & Economics**
- `gasPrice()` - Gas price for specific block or latest

### 🏛️ **Validator Operations**
- `validators()` - Current epoch validator information
- `EXPERIMENTALValidatorsOrdered()` - Validators in block producer order

### 🔬 **Experimental & Advanced**
- `EXPERIMENTALChanges()` - Account/contract changes (deprecated)
- `EXPERIMENTALChangesInBlock()` - Block-level changes (deprecated)
- `EXPERIMENTALCongestionLevel()` - Shard congestion information
- `EXPERIMENTALGenesisConfig()` - Genesis block parameters (deprecated)
- `EXPERIMENTALLightClientBlockProof()` - Light client proofs
- `EXPERIMENTALLightClientProof()` - Transaction execution proofs
- `EXPERIMENTALMaintenanceWindows()` - Node maintenance windows (deprecated)
- `EXPERIMENTALProtocolConfig()` - Protocol-level parameters
- `EXPERIMENTALReceipt()` - Receipt details by ID
- `EXPERIMENTALSplitStorageInfo()` - Split storage configuration
- `EXPERIMENTALTxStatus()` - Transaction status with full details

### 🌟 **Light Client**
- `lightClientProof()` - Transaction execution proofs
- `nextLightClientBlock()` - Next light client block

### 🏗️ **Genesis & Configuration**
- `genesisConfig()` - Initial genesis block parameters
- `maintenanceWindows()` - Current epoch maintenance windows

### 📊 **Response Types**
All methods return strongly typed `JsonElement` responses that can be deserialized into specific response types like:
- `RpcStatusResponse` - Network status information
- `RpcBlockResponse` - Block data and headers
- `RpcGasPriceResponse` - Gas pricing information
- `RpcQueryResponse` - Query results
- `RpcTransactionResponse` - Transaction execution details
- And 240+ other generated types for complete type safety

## 📄 License

Licensed under the Apache License, Version 2.0. See [LICENSE](LICENSE) for details.