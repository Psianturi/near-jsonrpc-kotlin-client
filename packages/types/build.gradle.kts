

plugins {
   kotlin("multiplatform") version "1.9.20"
   kotlin("plugin.serialization") version "1.9.20"
   id("org.jetbrains.kotlinx.kover") version "0.7.6"
   id("maven-publish")
}

kotlin {
   jvm()
   // JS target temporarily disabled to ensure root builds/tests don't require Node/Yarn locally.
   // Re-enable when JS coverage is needed:
   // js(IR) {
   //     browser()
   //     nodejs()
   // }
   // Add other targets as needed, e.g., native
   // iosX64()
   // iosArm64()
   // iosSimulatorArm64()

   sourceSets {
       val commonMain by getting {
           dependencies {
               implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
           }
       }
       val commonTest by getting {
           dependencies {
               implementation(kotlin("test"))
           }
       }
   }
}

publishing {
   publications {
       // Configure all publications with consistent artifact ID
       publications.withType<org.gradle.api.publish.maven.MavenPublication>().configureEach {
           artifactId = "near-jsonrpc-types"
       }
       
       // Remove JVM-only publication to avoid conflict with kotlinMultiplatform
       // Only publish kotlinMultiplatform which includes JVM target
       remove(findByName("jvm"))
   }
   repositories {
       maven {
           name = "GitHubPackages"
           url = uri("https://maven.pkg.github.com/Psianturi/near-jsonrpc-kotlin-client")
           credentials {
               username = System.getenv("GITHUB_ACTOR") ?: (findProperty("gpr.user") as String? ?: "")
               password = System.getenv("GITHUB_TOKEN") ?: (findProperty("gpr.key") as String? ?: "")
           }
       }
   }
}

koverReport {
    defaults {
        filters {
            excludes {
                // Exclude only generated data classes from coverage
                // But include any handwritten utility/helper classes
                classes(
                    "com.near.jsonrpc.types.AccessKey",
                    "com.near.jsonrpc.types.AccessKeyCreationConfigView",
                    "com.near.jsonrpc.types.AccessKeyInfoView",
                    "com.near.jsonrpc.types.AccessKeyList",
                    "com.near.jsonrpc.types.AccessKeyView",
                    "com.near.jsonrpc.types.AccountCreationConfigView",
                    "com.near.jsonrpc.types.AccountDataView",
                    "com.near.jsonrpc.types.AccountInfo",
                    "com.near.jsonrpc.types.AccountView",
                    "com.near.jsonrpc.types.AccountWithPublicKey",
                    "com.near.jsonrpc.types.ActionCreationConfigView",
                    "com.near.jsonrpc.types.ActionError",
                    "com.near.jsonrpc.types.Rpc*"  // All RPC request/response types
                    // Generated types are excluded, but tests will still run to verify serialization
                )
            }
        }
        verify {
            onCheck = false  // Disable coverage verification for types module (mostly generated code)
            // Coverage is tracked but not enforced for generated types
            // Focus is on ensuring types compile and basic serialization works
        }
    }
}
