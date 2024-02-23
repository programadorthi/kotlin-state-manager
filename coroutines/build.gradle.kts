plugins {
    kotlin("multiplatform")
    id("com.vanniktech.maven.publish")
    id("com.gradleup.nmcp")
}

applyBasicSetup()

darwinTargetsFramework()

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":core"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
            }
        }
    }
}

nmcp {
    publishAllPublications {}
}
