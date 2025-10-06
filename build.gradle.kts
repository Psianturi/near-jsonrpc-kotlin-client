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