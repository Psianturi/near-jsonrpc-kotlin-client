/**
 * Top-level Gradle configuration.
 * - Applies Kover to enable coverage tasks at the root (koverXmlReport, koverVerify).
 * - Sets group/version with dynamic override from -PreleaseVersion or env RELEASE_VERSION.
 */
plugins {
    `kotlin-dsl`
    id("org.jetbrains.kotlin.multiplatform") version "1.9.20" apply false
    id("org.jetbrains.kotlinx.kover") version "0.7.6"
    id("maven-publish")
    id("signing")
}

// Default group for published artifacts; overrideable via -Pgroup or env if needed
group = (findProperty("group") as String?) ?: System.getenv("GROUP") ?: "io.near"

// Version resolution preference (JitPack friendly):
// 1) -Pversion=1.2.3  (Project property)
// 2) -PreleaseVersion=1.2.3  (Legacy property)
// 3) VERSION (env injected by JitPack) or RELEASE_VERSION
// 4) Fallback snapshot for local/dev builds
val resolvedFromProps: String? = (findProperty("version") as String?)
    ?: (findProperty("releaseVersion") as String?)
val resolvedFromEnv: String? = System.getenv("VERSION")
    ?: System.getenv("RELEASE_VERSION")
// Prefer explicit version from CLI or CI env, otherwise fall back to a stable default
version = resolvedFromProps ?: resolvedFromEnv ?: "0.1.0"

// Global Kover report and verification settings
koverReport {
    defaults {
        xml {
            onCheck = true
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

// Maven Central Publishing Configuration
// Credentials should be provided via:
// - gradle.properties (local dev): ossrhUsername, ossrhPassword, signing.keyId, signing.password, signing.key
// - Environment variables (CI): OSSRH_USERNAME, OSSRH_PASSWORD, SIGNING_KEY_ID, SIGNING_PASSWORD, SIGNING_KEY
ext {
    set("ossrhUsername", findProperty("ossrhUsername") ?: System.getenv("OSSRH_USERNAME") ?: "")
    set("ossrhPassword", findProperty("ossrhPassword") ?: System.getenv("OSSRH_PASSWORD") ?: "")
    set("signingKeyId", findProperty("signing.keyId") ?: System.getenv("SIGNING_KEY_ID") ?: "")
    set("signingPassword", findProperty("signing.password") ?: System.getenv("SIGNING_PASSWORD") ?: "")
    set("signingKey", findProperty("signing.key") ?: System.getenv("SIGNING_KEY") ?: "")
}

// Configure subprojects for Maven Central publishing
subprojects {
    apply(plugin = "maven-publish")
    apply(plugin = "signing")
    
    configure<PublishingExtension> {
        repositories {
            maven {
                name = "OSSRH"
                val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
                
                credentials {
                    username = rootProject.ext["ossrhUsername"] as String
                    password = rootProject.ext["ossrhPassword"] as String
                }
            }
        }
        
        publications.withType<MavenPublication> {
            pom {
                name.set("NEAR JSON-RPC Kotlin Client")
                description.set("Type-safe Kotlin client for NEAR Protocol JSON-RPC API, generated from OpenAPI spec")
                url.set("https://github.com/Psianturi/near-jsonrpc-kotlin-client")
                
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                
                developers {
                    developer {
                        id.set("Psianturi")
                        name.set("NEAR Kotlin Client Contributors")
                        email.set("dev@near.org")
                    }
                }
                
                scm {
                    connection.set("scm:git:git://github.com/Psianturi/near-jsonrpc-kotlin-client.git")
                    developerConnection.set("scm:git:ssh://github.com/Psianturi/near-jsonrpc-kotlin-client.git")
                    url.set("https://github.com/Psianturi/near-jsonrpc-kotlin-client")
                }
            }
        }
    }
    
    configure<SigningExtension> {
        val signingKey = rootProject.ext["signingKey"] as String
        val signingPassword = rootProject.ext["signingPassword"] as String
        
        if (signingKey.isNotEmpty() && signingPassword.isNotEmpty()) {
            useInMemoryPgpKeys(signingKey, signingPassword)
            sign(the<PublishingExtension>().publications)
        }
    }
}