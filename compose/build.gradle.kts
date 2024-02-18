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
                api(project(":coroutines"))
                api(compose.runtime)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
            }
        }
        commonTest {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
            }
        }
    }
}