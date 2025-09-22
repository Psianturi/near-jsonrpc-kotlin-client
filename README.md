# NEAR JSON-RPC Kotlin Client

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.20-blue.svg)](https://kotlinlang.org)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![CI](https://github.com/Psianturi/near-jsonrpc-kotlin-client/actions/workflows/ci.yml/badge.svg)](https://github.com/Psianturi/near-jsonrpc-kotlin-client/actions/workflows/ci.yml)
[![Integration Tests](https://github.com/Psianturi/near-jsonrpc-kotlin-client/actions/workflows/integration.yml/badge.svg)](https://github.com/Psianturi/near-jsonrpc-kotlin-client/actions/workflows/integration.yml)
[![Code Quality](https://github.com/Psianturi/near-jsonrpc-kotlin-client/actions/workflows/lint.yml/badge.svg)](https://github.com/Psianturi/near-jsonrpc-kotlin-client/actions/workflows/lint.yml)

Kotlin Multiplatform JSON-RPC client for NEAR, generated from the official OpenAPI spec and designed for Android/JVM first. The code generator keeps models and client methods in sync with NEAR core, while CI ensures everything compiles, tests, and is ready for consumption.

- Two Kotlin modules:
  - near-jsonrpc-types (generated models + serialization)
  - near-jsonrpc-client (RPC client using Ktor + kotlinx.serialization)
- Fully automated regeneration via GitHub Actions (weekly/manual)
- JVM-first, Android-ready; minimal dependencies and ergonomic APIs

--------------------------------------------------------------------------------

## Background

The NEAR Protocol exposes a JSON-RPC interface and publishes an OpenAPI spec. However, a type-safe Kotlin client has been missing, slowing down native mobile and JVM developers. This repository provides:
- Automated code generation from NEAR’s OpenAPI spec
- Developer-friendly Kotlin APIs following Kotlin naming conventions
- Strong testing and CI automation for long-term maintainability
- Apache-2.0 Licensed public good

Note on OpenAPI vs implementation:
- The JSON-RPC implementation expects all methods to POST to the same path (“/”), while the OpenAPI lists unique paths per method. The transport intentionally ignores paths and always POSTs to the base RPC URL.

See the transport implementation here:
- [JsonRpcTransport.call()](packages/client/src/commonMain/kotlin/com/near/jsonrpc/JsonRpcTransport.kt:30)

--------------------------------------------------------------------------------

## Scope of Work (implemented)

1) OpenAPI parsing & code generation (TypeScript-based generator)
- Parse nearcore OpenAPI spec and generate Kotlin models and client methods.
- Apply snake_case (from API) → camelCase (Kotlin) naming.
- Typed endpoints (subset, expanding): status, validators, gas_price, query, block, EXPERIMENTAL_tx_status, send_tx, network_info, health.
- Patch: always POST to the base RPC endpoint path “/” (JSON-RPC 2.0).

2) Two Kotlin packages (Kotlin ecosystem naming)
- near-jsonrpc-types
  - Generated Kotlin data classes + kotlinx.serialization annotations.
  - Lightweight; only depends on kotlinx-serialization-json.
- near-jsonrpc-client
  - Depends on near-jsonrpc-types.
  - Ktor HTTP transport + JSON-RPC envelope + error handling.
  - Client wrapper exposing NEAR RPC endpoints.

3) GitHub Actions automation
- Fetch latest OpenAPI on schedule or manual dispatch.
- Regenerate Kotlin code.
- Build/test; submit PR for human review.
- Release-please automation prepares version bumps and release PRs.

4) Testing suite
- Unit tests for transport and serialization paths.
- Integration tests (manual/optional) against real NEAR RPC endpoints.

5) Documentation
- README with install and usage for both packages.
- Contribution guide and regenerating steps.
- CI/release workflows overview.

--------------------------------------------------------------------------------

## Architecture Overview

- Transport layer: posts JSON-RPC requests to a base URL (ignores per-method paths).
  - [JsonRpcTransport.call()](packages/client/src/commonMain/kotlin/com/near/jsonrpc/JsonRpcTransport.kt:30)
- Client layer: convenience wrapper for all NEAR endpoints.
  - Example endpoint method: [NearRpcClient.status()](packages/client/src/commonMain/kotlin/com/near/jsonrpc/client/NearRpcClient.kt:226)
- Types layer: generated data classes with kotlinx.serialization.

Modules and key files:
- near-jsonrpc-types
  - Gradle: [packages/types/build.gradle.kts](packages/types/build.gradle.kts)
- near-jsonrpc-client
  - Gradle: [packages/client/build.gradle.kts](packages/client/build.gradle.kts)

--------------------------------------------------------------------------------

## Installation (JitPack)

Add the JitPack repository in your settings:

settings.gradle.kts:
```kotlin
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

Add the dependency (use your release tag, e.g., 1.0.0):

build.gradle.kts:
```kotlin
dependencies {
    // Primary client (includes dependency on types)
    implementation("com.github.Psianturi:near-jsonrpc-client:1.0.0")

    // Optional: access types explicitly (usually not required)
    // implementation("com.github.Psianturi:near-jsonrpc-types:1.0.0")
}
```

Notes:
- Version 1.0.0 is available and tested on JitPack: https://jitpack.io/#Psianturi/near-jsonrpc-kotlin-client/v1.0.0
- For snapshots, you may use a branch or commit hash:
  - implementation("com.github.Psianturi:near-jsonrpc-client:master-SNAPSHOT")
  - implementation("com.github.Psianturi:near-jsonrpc-client:<commit-hash>")
- JDK 17+ recommended for builds.

--------------------------------------------------------------------------------

## Usage

Basic client usage (Android/JVM Ktor CIO):
```kotlin
import com.near.jsonrpc.JsonRpcTransport
import com.near.jsonrpc.client.NearRpcClient
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val http = HttpClient(CIO) {
        install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
    }

    // Transport posts JSON-RPC envelopes to the base RPC URL ("/")
    val transport = JsonRpcTransport(http, "https://rpc.testnet.near.org")
    val client = NearRpcClient(transport)

    // Query network status (typed)
    val status = client.status() // RpcStatusResponse
    println(status)

    http.close()
}
```

Typed decode via transport generics (advanced):
```kotlin
// You can use the transport directly with a typed result R when you know the schema:
data class StatusLike(val chainId: String?)
// val result: StatusLike = transport.call<JsonObject, StatusLike>("status", buildJsonObject {})
```

Notes:
- Subset endpoints are fully typed today: status, validators, gas_price. Other endpoints may return JsonElement and can be decoded into generated types as needed. Additional typed wrappers will be added incrementally.

--------------------------------------------------------------------------------

## Code Generation

OpenAPI source:
- https://github.com/near/nearcore/blob/master/chain/jsonrpc/openapi/openapi.json

Generator:
- TS/Node script under generator/ that fetches OpenAPI, applies small patches, and emits Kotlin.
- Always posts JSON-RPC to “/” regardless of per-method OpenAPI paths (implementation reality).

Local regeneration:
```bash
# Install generator deps
npm install --prefix generator

# Regenerate Kotlin code (types & client)
npm run generate --prefix generator

# Build generated code
./gradlew :packages:client:compileKotlinJvm
```

Automation:
- Weekly scheduled regeneration and manual workflow:
  - [openapi-autofetch.yml](.github/workflows/openapi-autofetch.yml)
  - [regen.yml](.github/workflows/regen.yml)
- CI is pinned to Node 22.x; generation caches npm dependencies.

--------------------------------------------------------------------------------

## Testing

- Coverage: Kover enforces ≥80% on the client module. Generated classes in the types module are excluded from coverage; additional tests will be added progressively.

Unit tests (transport logic, serialization):
```bash
# Run fast JVM unit tests (no network)
./gradlew :packages:client:jvmTest
```

Integration tests (manual/optional; hits real RPC):
```bash
# Optionally enable and run integration tests locally when needed
./gradlew :packages:client:jvmTest -Dgroups=integration
```

Example unit tests:
- [JsonRpcTransportTest.kt](packages/client/src/commonTest/kotlin/com/near/jsonrpc/JsonRpcTransportTest.kt)

Example integration tests:
- [NearRpcClientIntegrationTest.kt](packages/client/src/jvmTest/kotlin/com/near/jsonrpc/NearRpcClientIntegrationTest.kt)

Recent local verification (JVM):
```bash
./gradlew clean build
# BUILD SUCCESSFUL
```

--------------------------------------------------------------------------------

## GitHub Actions & Release Flow

Workflows:
- CI (build + unit tests): [ci.yml](.github/workflows/ci.yml)
- Integration tests (manual): [integration.yml](.github/workflows/integration.yml)
- Lint & code quality: [lint.yml](.github/workflows/lint.yml)
- Auto-fetch + regenerate from OpenAPI: [openapi-autofetch.yml](.github/workflows/openapi-autofetch.yml)
- Manual regenerate: [regen.yml](.github/workflows/regen.yml)
- Release automation: [release-please.yml](.github/workflows/release-please.yml), [release.yml](.github/workflows/release.yml)

Key points:
- Node 22.x for generator tasks.
- Regeneration opens PR with generated code.
- Release-please proposes version bumps and changelogs.
- Artifacts are currently consumed via JitPack; GitHub Packages/Maven Central can be added.

--------------------------------------------------------------------------------

## Contribution & Development

Prerequisites:
- JDK 17+
- Kotlin 1.9.20+
- Node.js 22+ (only needed for local codegen)

Dev workflow:
```bash
# Build everything (JVM)
./gradlew clean build

# Unit tests
./gradlew :packages:client:jvmTest

# Regenerate types/client
npm install --prefix generator
npm run generate --prefix generator
```

Guidelines:
- Add/adjust tests with code changes.
- Keep commit messages conventional (feat:, fix:, build:, ci:, docs:, test:, refactor:, chore:).
- Leave the transport path logic “as is” (always POST to “/”).

--------------------------------------------------------------------------------

## Deliverables Checklist

- ✅ Code generation pipeline (OpenAPI → Kotlin)
- ✅ Two Kotlin packages (types, client)
- ✅ GitHub Actions for regeneration, CI, lint, and release prep
- ✅ Unit tests and optional integration tests
- ✅ Developer documentation for usage, regeneration, and workflows
- ⏩ Typed wrappers per endpoint (incremental; transport already supports typed decode)

License: Apache-2.0 (see LICENSE)
