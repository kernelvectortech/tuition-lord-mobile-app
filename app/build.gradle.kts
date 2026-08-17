plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.kernelvector.tuitionlord"
    compileSdk {
        // compileSdk 36 (Android 9/15 — current Play Store requirement as of Aug 2026)
        // Defers Android 17-specific compile-time features to future phase
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.kernelvector.tuitionlord"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            // Load keystore properties from git-ignored file (if it exists)
            val keystoreFile = rootProject.file("keystore.properties")
            if (keystoreFile.exists()) {
                // Parse properties manually
                val props = mutableMapOf<String, String>()
                keystoreFile.forEachLine { line ->
                    val trimmed = line.trim()
                    if (trimmed.isNotEmpty() && !trimmed.startsWith("#")) {
                        val parts = trimmed.split("=", limit = 2)
                        if (parts.size == 2) {
                            props[parts[0].trim()] = parts[1].trim()
                        }
                    }
                }
                storeFile = if (props.containsKey("storeFile")) file(props["storeFile"]!!) else null
                storePassword = props["storePassword"]
                keyAlias = props["keyAlias"]
                keyPassword = props["keyPassword"]
            } else {
                // Dummy config to allow debug builds; will fail when release build is attempted
                storeFile = file("dummy.jks")
                storePassword = "dummy"
                keyAlias = "dummy"
                keyPassword = "dummy"
            }
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions {
            jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
        }
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.play.services.ads)
    
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}