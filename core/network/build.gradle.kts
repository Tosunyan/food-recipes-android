import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.tosunyan.foodrecipes.network"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        val properties = gradleLocalProperties(rootDir, providers)
        buildConfigField(
            type = "String",
            name = "MEAL_API_BASE_URL",
            value = properties.getProperty("MEAL_API_BASE_URL")
        )
        buildConfigField(
            type = "String",
            name = "MEAL_API_KEY",
            value = properties.getProperty("MEAL_API_KEY")
        )
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.di)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization)

    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.okhttp)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
}