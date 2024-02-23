plugins {
    kotlin("multiplatform")
    id("com.vanniktech.maven.publish")
    id("com.gradleup.nmcp")
}

applyBasicSetup()

darwinTargetsFramework()

nmcp {
    publishAllPublications {}
}
