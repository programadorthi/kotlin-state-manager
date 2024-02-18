plugins {
    kotlin("multiplatform")
    id("com.vanniktech.maven.publish")
}

applyBasicSetup()

darwinTargetsFramework()

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(project(":core"))
            }
        }
    }
}