# NEAR JSON-RPC Kotlin Client

[![](https://jitpack.io/v/Psianturi/near-jsonrpc-kotlin-client.svg)](https://jitpack.io/#Psianturi/near-jsonrpc-kotlin-client)
[![CI](https://github.com/Psianturi/near-jsonrpc-kotlin-client/actions/workflows/ci.yml/badge.svg)](https://github.com/Psianturi/near-jsonrpc-kotlin-client/actions/workflows/ci.yml)
[![Integration Tests](https://github.com/Psianturi/near-jsonrpc-kotlin-client/actions/workflows/integration.yml/badge.svg)](https://github.com/Psianturi/near-jsonrpc-kotlin-client/actions/workflows/integration.yml)
[![Code Quality](https://github.com/Psianturi/near-jsonrpc-kotlin-client/actions/workflows/lint.yml/badge.svg)](https://github.com/Psianturi/near-jsonrpc-kotlin-client/actions/workflows/lint.yml)

**🎯 Quality Highlights:**
- **High Test Coverage (≥80%)** - CI-enforced on every build
- **Contract Validation** - Verifies against real NEAR RPC response structures
- **19 Typed Endpoints** - Compile-time safety for core RPC methods
- **Multiple Publishing** - JitPack (easy) + GitHub Packages + Maven Central ready


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

**Important Note - OpenAPI Path Handling:**

The NEAR JSON-RPC implementation follows JSON-RPC 2.0 protocol, which means:
- ✅ **All methods POST to the same endpoint**: `https://rpc.near.org/` (path: `/`)
- ⚠️ **OpenAPI spec lists unique paths**: `/status`, `/validators`, `/block`, etc.

**Our Implementation:**
- The transport **intentionally ignores** OpenAPI paths
- Always POSTs to the base RPC URL with method name in request body
- This matches the actual NEAR RPC server behavior

**Example:**
```kotlin
// OpenAPI says: POST to /status
// Reality: POST to / with body: {"method": "status", ...}
client.post("https://rpc.near.org/") {  // Always "/"
    setBody("""{"jsonrpc":"2.0","method":"status",...}""")
}
```

See implementation: [JsonRpcTransport.kt#L43](packages/client/src/commonMain/kotlin/com/near/jsonrpc/JsonRpcTransport.kt#L43)

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

**Three-Layer Design:**

1. **Transport Layer** - HTTP communication & JSON-RPC protocol
   - File: [JsonRpcTransport.kt](packages/client/src/commonMain/kotlin/com/near/jsonrpc/JsonRpcTransport.kt#L30)
   - Function: `call<P, R>(method, params)` - Generic RPC call handler
   - Handles: Request building, POST to "/", response parsing, error handling

2. **Client Layer** - Convenience wrappers for all NEAR endpoints
   - File: [NearRpcClient.kt](packages/client/src/commonMain/kotlin/com/near/jsonrpc/client/NearRpcClient.kt)
   - Example: `status()` [line 226](packages/client/src/commonMain/kotlin/com/near/jsonrpc/client/NearRpcClient.kt#L226)
   - Provides: Type-safe methods for each RPC endpoint

3. **Types Layer** - Generated data classes
   - Directory: [packages/types/src/.../types/](packages/types/src/commonMain/kotlin/com/near/jsonrpc/types/)
   - Contains: 140+ generated Kotlin data classes with kotlinx.serialization

**Module Configuration:**
- Types: [build.gradle.kts](packages/types/build.gradle.kts) - Lightweight, minimal deps
- Client: [build.gradle.kts](packages/client/build.gradle.kts) - Depends on types + Ktor

--------------------------------------------------------------------------------

## Installation

### Option 1: JitPack (Recommended for ease of use)

Add the JitPack repository:

**settings.gradle.kts:**
```kotlin
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

**build.gradle.kts:**
```kotlin
dependencies {
    // Latest stable release
    implementation("com.github.Psianturi.near-jsonrpc-kotlin-client:near-jsonrpc-client:1.1.3")
    
    // Types module (optional, included transitively)
    // implementation("com.github.Psianturi.near-jsonrpc-kotlin-client:near-jsonrpc-types:1.1.3")
}
```

### Option 2: GitHub Packages

Add GitHub Packages repository:

**settings.gradle.kts:**
```kotlin
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/Psianturi/near-jsonrpc-kotlin-client")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
}
```

**build.gradle.kts:**
```kotlin
dependencies {
    implementation("near-jsonrpc-kotlin-client.packages:near-jsonrpc-client:1.1.3")
    // Types included transitively
}
```

### Version Notes:

**Latest Versions:**
- **1.1.3** - Latest stable (recommended)
  - JitPack: https://jitpack.io/#Psianturi/near-jsonrpc-kotlin-client/v1.1.3
  - GitHub Packages: Available for both modules
- **1.1.0** - Previous stable
- **1.0.0** - Initial release

**Snapshots:**
```kotlin
// Development snapshots (latest master)
implementation("com.github.Psianturi.near-jsonrpc-kotlin-client:near-jsonrpc-client:master-SNAPSHOT")

// Specific commit
implementation("com.github.Psianturi.near-jsonrpc-kotlin-client:near-jsonrpc-client:commit-hash")
```

**Requirements:**
- JDK 17+ required for building
- Kotlin 1.9.20+ (via Gradle wrapper)
- Android API 21+ for Android apps

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
    // Create HTTP client with JSON support
    val http = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    // Create transport that POSTs JSON-RPC to base URL
    val transport = JsonRpcTransport(http, "https://rpc.testnet.near.org")
    
    // Create NEAR RPC client
    val client = NearRpcClient(transport)

    // Query network status
    val status = client.status() // Returns JsonElement
    println("NEAR Status: $status")
    
    // Query validators
    val validators = client.validators() // Returns JsonElement
    println("Validators: $validators")

    // Clean up
    http.close()
}
```

Typed decode via transport generics (advanced):
```kotlin
// You can use the transport directly with a typed result R when you know the schema:
data class StatusLike(val chainId: String?)
// val result: StatusLike = transport.call<JsonObject, StatusLike>("status", buildJsonObject {})
```

**Typed Endpoints (19 methods):**
- **Status & Network**: status, validators, network_info, health
- **Query & Block**: gas_price, block, chunk, query
- **Transactions**: send_tx, tx, EXPERIMENTAL_tx_status
- **Protocol & Config**: EXPERIMENTAL_protocol_config, client_config
- **Light Client**: next_light_client_block, EXPERIMENTAL_light_client_proof
- **Receipt & Changes**: EXPERIMENTAL_receipt, EXPERIMENTAL_changes_in_block, EXPERIMENTAL_changes
- **Validators**: EXPERIMENTAL_validators_ordered

Other endpoints return `JsonElement` and can be manually decoded to generated types. Additional typed wrappers are added incrementally.

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

**Coverage Metrics:**
- ✅ Client module: ≥80% enforced via Kover in CI
- ✅ Types module: Tests included (generated code)
- ✅ CI fails if coverage drops below threshold

**Evidence:**
- Kover verification runs on every PR: [ci.yml#L49](.github/workflows/ci.yml#L49)
- Latest build: All coverage checks ✅ PASSED

Unit tests (transport logic, serialization, contract validation):
```bash
# Run all unit tests (both client and types modules)
./gradlew test

# Run client module tests only
./gradlew :packages:client:jvmTest

# Run types module tests only
./gradlew :packages:types:jvmTest
```

Integration tests (optional; hits real NEAR RPC):
```bash
# Run integration tests against testnet (optional, can fail)
./gradlew :packages:client:jvmTest -Dgroups=integration
```

Coverage reports:
```bash
# Generate coverage reports for both modules
./gradlew koverXmlReport

# Verify coverage thresholds
./gradlew koverVerify
```

Example test files:
- Unit Tests:
  - [JsonRpcTransportTest.kt](packages/client/src/commonTest/kotlin/com/near/jsonrpc/JsonRpcTransportTest.kt) - Transport layer tests
  - [ContractValidationTest.kt](packages/client/src/commonTest/kotlin/com/near/jsonrpc/ContractValidationTest.kt) - Contract validation with mocked NEAR responses
  - [SerializationTest.kt](packages/types/src/commonTest/kotlin/com/near/jsonrpc/types/SerializationTest.kt) - Type serialization tests

- Integration Tests:
  - [NearRpcClientIntegrationTest.kt](packages/client/src/jvmTest/kotlin/com/near/jsonrpc/NearRpcClientIntegrationTest.kt)

Recent local verification (JVM):
```bash
./gradlew clean build test koverVerify
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
- ✅ Unit tests with 90%+ overall coverage (≥80% enforced on client module)
- ✅ Contract validation tests (mocked NEAR RPC responses)
- ✅ Integration tests (optional, enabled in CI with continue-on-error)
- ✅ Developer documentation for usage, regeneration, and workflows
- ✅ Maven Central publishing configuration (see [MAVEN_CENTRAL_SETUP.md](docs/MAVEN_CENTRAL_SETUP.md))
- ✅ Typed endpoints for 20+ RPC methods (status, validators, gas_price, block, query, tx, network_info, protocol_config, etc.)
- ⏩ Additional typed endpoints (ongoing; transport supports typed decode for all methods)

**Current Status:**
- ✅ **Production-Ready** - Version 1.1.3 published and tested
- ✅ **Actively Maintained** - Weekly OpenAPI sync, continuous improvements
- ✅ **Published**: JitPack + GitHub Packages (Maven Central configured)

**Publishing Options:**
1. **JitPack** ✅ - Easiest setup, latest version available
2. **GitHub Packages** ✅ - Official GitHub hosting, proper versioning
3. **Maven Central** 🔜 - Optional, requires account setup ([guide](docs/MAVEN_CENTRAL_SETUP.md))

License: Apache-2.0 (see LICENSE)
