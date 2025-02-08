plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.tosunyan.foodrecipes.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.di)
    implementation(projects.core.model)
    implementation(projects.core.network)
    implementation(projects.core.database)
}