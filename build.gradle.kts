plugins {
    kotlin("plugin.serialization") version "1.9.22" apply false
    id("org.jetbrains.compose") version "1.6.0-beta02" apply false
    id("com.vanniktech.maven.publish") version "0.27.0" apply false
    id("com.gradleup.nmcp") version "0.0.4"
}

group = "dev.programadorthi.state"

tasks.register("runJvmTests") {
    group = "other"
    description = "Execute JVM tests in each module only"
}

nmcp {
    publishAggregation {
        project(":core")
        project(":coroutines")
        project(":compose")
        project(":validators")

        username = project.providers.gradleProperty("mavenCentralUsername")
        password = project.providers.gradleProperty("mavenCentralPassword")
        publicationType = "USER_MANAGED"
    }
}
