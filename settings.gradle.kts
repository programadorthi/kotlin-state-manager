pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

dependencyResolutionManagement {
// FIXME: Build was configured to prefer settings repositories over project repositories but repository 'Node Distributions at https://nodejs.org/dist' was added by unknown code
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "kotlin-state-manager"

include("core")
include("coroutines")
//include("compose")
/*
include(":samples:compose:norris-facts:android")
include(":samples:compose:norris-facts:common")
include(":samples:compose:norris-facts:desktop")
*/