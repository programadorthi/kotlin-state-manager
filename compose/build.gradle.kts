plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("com.vanniktech.maven.publish")
}

applyBasicSetup()

darwinTargetsFramework()

kotlin {
    androidTarget {
        publishLibraryVariants("release")
    }

    sourceSets {
        commonMain {
            dependencies {
                api(project(":coroutines"))
                api(compose.runtime)
                api(compose.runtimeSaveable)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
            }
        }
        commonTest {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
            }
        }
        androidMain {
            dependencies {
                implementation("androidx.activity:activity-ktx:1.8.2")
                implementation("androidx.fragment:fragment-ktx:1.6.2")
            }
        }
    }
}

android {
    namespace = "dev.programadorthi.state.compose"
    compileSdk = 34

    defaultConfig {
        minSdk = 23
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    sourceSets["main"].manifest.srcFile("src/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/res")
}
