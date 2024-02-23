plugins {
    kotlin("multiplatform")
    id("com.vanniktech.maven.publish")
    id("com.gradleup.nmcp")
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

nmcp {
    publishAllPublications {}
}
