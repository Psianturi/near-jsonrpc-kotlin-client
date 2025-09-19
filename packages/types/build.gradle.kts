
plugins {
    kotlin("multiplatform") version "1.9.20"
    kotlin("plugin.serialization") version "1.9.20"
}

kotlin {
    jvm()
    js(IR) {
        browser()
        nodejs()
    }
    // Add other targets as needed, e.g., native
    //iosX64()
    //iosArm64()
    //iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("com.github.Psianturi:near-jsonrpc-kotlin-client:0.1.0") 
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
                implementation("io.ktor:ktor-client-okhttp:2.3.7") // Android HTTP transport
            }
        }
    }
}