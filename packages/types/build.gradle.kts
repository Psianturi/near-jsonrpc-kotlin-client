

plugins {
    kotlin("multiplatform") version "1.9.20"
    kotlin("plugin.serialization") version "1.9.20"
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
    //iosX64()
    //iosArm64()
    //iosSimulatorArm64()

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
