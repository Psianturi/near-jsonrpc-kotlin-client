# Android Integration Guide

## 📱 Complete Example Application

**A full-featured Android application demonstrating this library is available at:**
🔗 **[https://github.com/Psianturi/near-kotlin](https://github.com/Psianturi/near-kotlin)**

### Example App Features:
- ✅ **MVVM Architecture** - Proper ViewModel with StateFlow
- ✅ **15+ RPC Endpoints** - Network info, status, blocks, validators, etc.
- ✅ **Wallet Integration** - NEAR Wallet connection via Custom Tabs
- ✅ **Contract Interaction** - View method calls with parameters
- ✅ **Account Queries** - Query any NEAR account information
- ✅ **Error Handling** - Comprehensive error types and user feedback
- ✅ **Modern UI** - Jetpack Compose with Material 3
- ✅ **Production Ready** - Complete with tests and documentation

### Quick Start with Example App:
```bash
# Clone the example app
git clone https://github.com/Psianturi/near-kotlin
cd near-kotlin

# Open in Android Studio and run
# The app includes comprehensive usage examples
```

---

## Package Names

The library uses the following package structure:

```kotlin
// Main client class
import com.near.jsonrpc.client.NearRpcClient

// Transport layer
import com.near.jsonrpc.JsonRpcTransport

// Generated types (if needed)
import com.near.jsonrpc.types.*
```

## IDE Import Setup

### Android Studio / IntelliJ IDEA:

#### 1. Project Setup:
- Ensure the library is added as a composite build in `settings.gradle.kts`
- Sync project with Gradle files (button or File → Sync Project with Gradle Files)

#### 2. Auto-Import Configuration:
```
File → Settings → Editor → General → Auto Import
```
- ✅ Enable "Add unambiguous imports on the fly"
- ✅ Enable "Optimize imports on the fly"
- ✅ Set "Insert imports for inner classes" to "Ask"

#### 3. Manual Import Methods:

**Method A - Quick Fix:**
- Place cursor on red/underlined class name
- Press `Alt + Enter` (Windows/Linux) or `Option + Enter` (Mac)
- Select the correct import from suggestions

**Method B - Using Statement:**
- Type the full class name (e.g., `NearRpcClient`)
- Android Studio will show red underline
- Use `Alt + Enter` to add import

**Method C - Manual Addition:**
- Add import statements manually at the top of your file:
```kotlin
import com.near.jsonrpc.client.NearRpcClient
import com.near.jsonrpc.JsonRpcTransport
```

#### 4. Import Optimization:
- Use `Ctrl + Alt + O` (Windows/Linux) or `Cmd + Alt + O` (Mac) to optimize imports
- Remove unused imports automatically
- Organize imports alphabetically

#### 5. Troubleshooting Imports:
- **Red squiggly lines**: Check if library is properly included in build
- **"Cannot resolve symbol"**: Try Build → Clean Project, then Rebuild
- **Multiple import options**: Choose the one from `com.near.jsonrpc.*`

## Setup Dependencies

### Option 1: Composite Build (Recommended for Development)

Add to `settings.gradle.kts`:
```kotlin
includeBuild("../near-jsonrpc-kotlin-client") {
    dependencySubstitution {
        substitute(module("com.near:jsonrpc-client")).using(project(":packages:client"))
        substitute(module("com.near:jsonrpc-types")).using(project(":packages:types"))
    }
}
```

Add to `app/build.gradle.kts`:
```kotlin
dependencies {
    // NEAR JSON-RPC Kotlin Client
    implementation("com.near:jsonrpc-client:1.0.0")
    implementation("com.near:jsonrpc-types:1.0.0")

    // Ktor for HTTP client
    implementation("io.ktor:ktor-client-okhttp:2.3.4")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.4")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.4")

    // Kotlinx Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
}
```

### Option 2: Local Maven Repository

1. Publish to local Maven:
```bash
cd near-jsonrpc-kotlin-client
./gradlew publishToMavenLocal
```

2. Add to `settings.gradle.kts`:
```kotlin
dependencyResolutionManagement {
    repositories {
        mavenLocal()
    }
}
```

### Option 3: JitPack (Recommended for Production)

Add JitPack repository to `settings.gradle.kts`:
```kotlin
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

Add to `app/build.gradle.kts`:
```kotlin
dependencies {
    // NEAR JSON-RPC Kotlin Client
    implementation("com.github.Psianturi.near-jsonrpc-kotlin-client:near-jsonrpc-client:1.1.3")
    // Types included transitively, no need to add separately

    // Ktor for HTTP client
    implementation("io.ktor:ktor-client-okhttp:2.3.4")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.4")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.4")

    // Kotlinx Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
}
```

Notes:
- Latest: 1.1.3 - https://jitpack.io/#Psianturi/near-jsonrpc-kotlin-client/v1.1.3
- Snapshots: implementation("com.github.Psianturi.near-jsonrpc-kotlin-client:near-jsonrpc-client:master-SNAPSHOT")

## Usage Example

### ✅ Correct Implementation:
```kotlin
package com.yourcompany.yourapp

import com.near.jsonrpc.client.NearRpcClient
import com.near.jsonrpc.JsonRpcTransport
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class NearService {
    private val httpClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }
    }

    private val transport = JsonRpcTransport(
        client = httpClient,
        rpcUrl = "https://rpc.testnet.near.org"
    )

    private val client = NearRpcClient(transport)

    suspend fun getStatus() = client.status()
    suspend fun getBlock() = client.block()
    suspend fun getGasPrice() = client.gasPrice()

    fun close() {
        httpClient.close()
    }
}
```

### ❌ Wrong Implementation (Don't Do This):
```kotlin
// WRONG IMPORTS!
import com.github.psianturi.near.client.NearRpcClient      // ❌ Wrong package
import com.github.psianturi.near.transport.JsonRpcTransport // ❌ Wrong package

class NearService {
    private val client = NearRpcClient(
        transport = JsonRpcTransport(
            baseUrl = "https://rpc.testnet.near.org",      // ❌ Wrong parameter name
            httpClient = HttpClient(OkHttp)
        )
    )
}
```

## Troubleshooting

### Build Issues:

1. **"No connected devices!" when running `installDebug`:**
   ```bash
   # Solution 1: Start Android Emulator
   # Open Android Studio → Device Manager → Create Virtual Device → Run

   # Solution 2: Connect Physical Device
   # Enable USB Debugging in Developer Options
   # Connect via USB and allow debugging

   # Solution 3: Build APK Only (No Installation)
   ./gradlew assembleDebug
   # APK will be in: app/build/outputs/apk/debug/app-debug.apk
   ```

2. **"INSTALL_FAILED_INSUFFICIENT_STORAGE":**
   ```bash
   # Free up space on device/emulator
   adb shell pm list packages  # See installed apps
   adb uninstall <package_name>  # Remove unused apps
   ```

3. **"Unresolved reference" errors:**
   - Ensure composite build is properly configured in `settings.gradle.kts`
   - Check that the relative path to `near-jsonrpc-kotlin-client` is correct
   - Try: Build → Clean Project, then Build → Rebuild Project

4. **Gradle sync fails:**
   - File → Invalidate Caches / Restart
   - Delete `.gradle` folder and resync
   - Check that all dependencies are available

5. **Kotlin compilation errors:**
   - Ensure Kotlin version compatibility (1.9.20+)
   - Check that all required plugins are applied
   - Verify JVM target compatibility

### Runtime Issues:

1. **Network connection fails:**
   - Check internet connectivity
   - Verify RPC endpoint URL is correct
   - Ensure proper network permissions in AndroidManifest.xml

2. **Serialization errors:**
   - Verify JSON structure matches expected types
   - Check that `ignoreUnknownKeys = true` is set
   - Ensure proper error handling for malformed responses

## Testing Your Setup

### Build Commands:

#### Build APK Only (No Installation):
```bash
# Build debug APK without installing
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Build and install (requires connected device/emulator)
./gradlew installDebug
```

#### Common Build Issues:

**Issue: "No connected devices!"**
```bash
# Solution 1: Start Android Emulator
# Open Android Studio → Device Manager → Create Virtual Device → Run

# Solution 2: Connect Physical Device
# Enable USB Debugging in Developer Options
# Connect via USB and allow debugging

# Solution 3: Build APK Only
./gradlew assembleDebug
# APK will be in: app/build/outputs/apk/debug/app-debug.apk
```

**Issue: "INSTALL_FAILED_INSUFFICIENT_STORAGE"**
```bash
# Free up space on device/emulator
adb shell pm list packages  # See installed apps
adb uninstall <package_name>  # Remove unused apps
```

### Basic Connectivity Test:

Create a simple test function in your Android activity or ViewModel:

```kotlin
// In your Activity or ViewModel
suspend fun testNearConnection() {
    val service = NearService()
    try {
        val status = service.getStatus()
        println("✅ NEAR connection successful: $status")
        // Update UI to show success
    } catch (e: Exception) {
        println("❌ NEAR connection failed: ${e.message}")
        // Update UI to show error
    } finally {
        service.close()
    }
}
```

### Android Permissions:

Add to `AndroidManifest.xml`:
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

### UI Event Handling:

**Important**: Since NEAR service methods are `suspend` functions, you must call them within a coroutine scope:

```kotlin
// ✅ CORRECT - Using lifecycleScope.launch
Button(onClick = {
    lifecycleScope.launch {
        try {
            val result = nearService.getStatus()
            status = result.toString()
        } catch (e: Exception) {
            errorMessage = e.message
        }
    }
}) {
    Text("Get Status")
}

// ❌ WRONG - Cannot call suspend function directly
Button(onClick = {
    val result = nearService.getStatus() // Compilation error!
    status = result.toString()
}) {
    Text("Get Status")
}
```

### State Management:

Use Compose state to automatically update UI when data changes:

```kotlin
var status by remember { mutableStateOf<String?>(null) }
var isLoading by remember { mutableStateOf(false) }
var errorMessage by remember { mutableStateOf<String?>(null) }

// State will automatically trigger UI recomposition
status?.let { /* Display status */ }
errorMessage?.let { /* Display error */ }
if (isLoading) { /* Show loading indicator */ }
```

### Coroutine Setup:

For Android activities/fragments, use proper coroutine scope:

```kotlin
class MainActivity : AppCompatActivity() {
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ... setup UI

        scope.launch {
            testNearConnection()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}
```

### ViewModel Integration:

```kotlin
class NearViewModel : ViewModel() {
    private val service = NearService()

    fun fetchNetworkStatus() = liveData(Dispatchers.IO) {
        try {
            val status = service.getStatus()
            emit(Result.success(status))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun onCleared() {
        super.onCleared()
        service.close()
    }
}
```

## Troubleshooting

1. **Clean and rebuild**: `./gradlew clean build`
2. **Invalidate caches**: Android Studio → File → Invalidate Caches / Restart
3. **Check Gradle sync**: Make sure all dependencies are resolved
4. **Verify composite build**: Ensure the path to near-jsonrpc-kotlin-client is correct

## Available RPC Methods

The `NearRpcClient` provides access to all major NEAR RPC endpoints:

### Network Information:
```kotlin
suspend fun status(): JsonElement           // Network status & version
suspend fun networkInfo(): JsonElement      // Network connections info
```

### Block Operations:
```kotlin
suspend fun block(): JsonElement            // Latest final block
suspend fun blockEffects(): JsonElement     // Block transaction effects
```

### Account Operations:
```kotlin
suspend fun query(): JsonElement            // Generic account/contract queries
```

### Transaction Operations:
```kotlin
suspend fun sendTx(): JsonElement           // Send transaction
suspend fun tx(): JsonElement               // Get transaction by hash
```

### Gas Operations:
```kotlin
suspend fun gasPrice(): JsonElement         // Current gas price
```

### Validator Operations:
```kotlin
suspend fun validators(): JsonElement       // Current validators
```

### Additional Methods:
```kotlin
suspend fun clientConfig(): JsonElement     // Client configuration
suspend fun genesisConfig(): JsonElement    // Genesis block config
suspend fun health(): JsonElement           // Node health status
suspend fun chunk(): JsonElement            // Chunk details
suspend fun changes(): JsonElement          // Account/contract changes
```

## Next Steps

Once setup is working:
1. Choose the RPC methods you need from the list above
2. Add proper error handling with try-catch blocks
3. Implement loading states in your UI
4. Add offline handling for network failures
5. Consider implementing caching for frequently accessed data
6. Add unit tests for your service layer
7. Handle different NEAR networks (mainnet/testnet)