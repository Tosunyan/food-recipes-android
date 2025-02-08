plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.tosunyan.foodrecipes.ui"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.di)
    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(projects.core.database)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.activity)
    implementation(libs.compose.viewmodel)
    implementation(libs.compose.designsystem)
    implementation(libs.compose.constraintlayout)
    implementation(libs.compose.material3)

    implementation(libs.voyager.navigator)
    implementation(libs.voyager.tab.navigator)
    implementation(libs.voyager.transitions)

    implementation(libs.coil)
    implementation(libs.textflow)

    debugImplementation(libs.compose.ui.tooling)
}