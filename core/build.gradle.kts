plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.vanniktech.maven.publish")
}

applyBasicSetup()

darwinTargetsFramework()

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(compose.runtime)
            }
        }
        commonTest {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
            }
        }
    }
}