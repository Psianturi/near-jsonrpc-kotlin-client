plugins {
    kotlin("multiplatform") version "1.9.20"
    kotlin("plugin.serialization") version "1.9.20"
}

kotlin {
    jvm()
    // Temporarily disabled JS target to avoid Node.js dependency issues
    // js(IR) {
    //     browser()
    //     nodejs()
    // }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":packages:types"))
                // Depend on the types we just generated
                implementation(project(":packages:types"))

                // Ktor for HTTP client capabilities
                implementation("io.ktor:ktor-client-core:2.3.4")
                implementation("io.ktor:ktor-client-content-negotiation:2.3.4")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.4")

                // kotlinx.serialization for JsonElement
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                // Dependency for Ktor mock client
                implementation("io.ktor:ktor-client-mock:2.3.4")
                // Coroutines test for runTest
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                // Ktor client for JVM integration tests
                implementation("io.ktor:ktor-client-cio:2.3.4")
                implementation("io.ktor:ktor-client-content-negotiation:2.3.4")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.4")
            }
        }
    }
    
    // Configure test tasks to exclude integration tests by default
    tasks.withType<Test> {
        useJUnitPlatform {
            excludeTags("integration")
        }
    }

    // Note: Integration tests can be run using:
    // ./gradlew :packages:client:jvmTest --tests "*NearRpcClientIntegrationTest*"
    // or with tags: ./gradlew :packages:client:jvmTest -Dgroups=integration
}