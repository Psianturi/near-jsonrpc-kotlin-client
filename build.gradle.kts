/**
 * Top-level Gradle configuration.
 * - Applies Kover to enable coverage tasks at the root (koverXmlReport, koverVerify).
 * - Sets group/version with dynamic override from -PreleaseVersion or env RELEASE_VERSION.
 */
plugins {
    `kotlin-dsl`
    id("org.jetbrains.kotlin.multiplatform") version "1.9.20" apply false
    id("org.jetbrains.kotlinx.kover") version "0.7.6"
}

group = "com.github.psianturi"

// Version resolution preference:
// 1) Gradle property: -PreleaseVersion=1.2.3
// 2) Environment variable: RELEASE_VERSION=1.2.3
// 3) Fallback snapshot for local/dev builds
val resolvedReleaseVersion: String? = (findProperty("releaseVersion") as String?) ?: System.getenv("RELEASE_VERSION")
version = resolvedReleaseVersion ?: "0.1.0-SNAPSHOT"

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