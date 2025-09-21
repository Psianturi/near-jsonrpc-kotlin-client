

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
       publications.withType<org.gradle.api.publish.maven.MavenPublication>().configureEach {
           artifactId = "near-jsonrpc-types"
       }
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
                // Exclude generated classes from coverage calculation to focus on handwritten logic
                // All classes in types are generated from OpenAPI, so exclude the entire package
                classes("com.near.jsonrpc.types.*")
            }
        }
        verify {
            onCheck = true
            rule {
                bound {
                    minValue = 80
                }
            }
        }
    }
}
