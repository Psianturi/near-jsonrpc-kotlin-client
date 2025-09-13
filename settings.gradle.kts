pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "near-jsonrpc-kotlin-client"

include(":packages:types")
// include(":packages:client") // Will be enabled later