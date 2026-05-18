plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
}

kotlin {
    jvmToolchain(21)
}

android {
    namespace = "com.tosunyan.foodrecipes.ui"
    compileSdk = 36

    defaultConfig {
        minSdk = 26
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
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

    implementation(platform(libs.compose.bom))
    api(libs.compose.foundation)
    api(libs.compose.ui.tooling.preview)
    api(libs.compose.activity)
    api(libs.compose.viewmodel)
    api(libs.compose.designsystem)
    api(libs.compose.constraintlayout)
    api(libs.compose.material3)

    api(libs.voyager.navigator)
    api(libs.voyager.tab.navigator)
    api(libs.voyager.transitions)

    api(libs.coil)
    api(libs.textflow)

    debugImplementation(libs.compose.ui.tooling)
}