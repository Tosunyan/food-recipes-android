import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    var properties = gradleLocalProperties(rootDir, providers)

    compileSdk = 34

    defaultConfig {
        applicationId = "com.tosunyan.foodrecipes"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "0.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("$rootDir/debug.keystore")
            storePassword = properties.getProperty("STORE_PASSWORD")
            keyAlias = properties.getProperty("KEY_ALIAS")
            keyPassword = properties.getProperty("STORE_PASSWORD")
        }

        create("release") {
            properties = Properties().apply {
                load(rootProject.file("prod.properties").inputStream())
            }

            storeFile = file("$rootDir/release.keystore")
            storePassword = properties.getProperty("STORE_PASSWORD")
            keyAlias = properties.getProperty("KEY_ALIAS")
            keyPassword = properties.getProperty("STORE_PASSWORD")
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false

            signingConfig = signingConfigs.getByName("debug")
        }

        getByName("release") {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    namespace = "com.tosunyan.foodrecipes"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.di)
    implementation(projects.core.ui)
    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(projects.core.database)

    implementation(libs.appcompat)
}