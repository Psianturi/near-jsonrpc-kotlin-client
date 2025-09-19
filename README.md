# NEAR JSON-RPC Kotlin Client

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.20-blue.svg)](https://kotlinlang.org)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![CI](https://github.com/Psianturi/near-jsonrpc-kotlin-client/actions/workflows/ci.yml/badge.svg)](https://github.com/Psianturi/near-jsonrpc-kotlin-client/actions/workflows/ci.yml)
[![Integration Tests](https://github.com/Psianturi/near-jsonrpc-kotlin-client/actions/workflows/integration.yml/badge.svg)](https://github.com/Psianturi/near-jsonrpc-kotlin-client/actions/workflows/integration.yml)

A type-safe Kotlin client for NEAR blockchain JSON-RPC API. Connect to NEAR mainnet and testnet with full type safety and automatic code generation from the official OpenAPI specification.

[🐛 Report Bug](https://github.com/Psianturi/near-jsonrpc-kotlin-client/issues) • [💡 Request Feature](https://github.com/Psianturi/near-jsonrpc-kotlin-client/issues) • [🤝 Contributing](https://github.com/Psianturi/near-jsonrpc-kotlin-client/blob/main/CONTRIBUTING.md) • [📋 Changelog](https://github.com/Psianturi/near-jsonrpc-kotlin-client/blob/main/CHANGELOG.md)

## ✨ Features

- **Type-Safe API** - Strongly typed methods with compile-time safety
- **Auto-Generated** - Code generated from official NEAR OpenAPI specification
- **Multiplatform** - JVM support with Android compatibility
- **Production Ready** - Tested with real NEAR networks
- **Comprehensive Testing** - Unit and integration tests included

## 🚀 Quick Start

### Installation

Add to your `build.gradle.kts`:

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

## 🧪 Testing

```bash
# Unit tests (fast, no network)
./gradlew :packages:client:jvmTest

# Integration tests (real NEAR network)
./gradlew :packages:client:jvmTest -Dgroups=integration

# Full build with all tests
./gradlew build
```

## 🤝 Contributing

We welcome contributions! Please:

1. Fork the repository
2. Create a feature branch
3. Add tests for new functionality
4. Ensure `./gradlew build` passes
5. Submit a pull request

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