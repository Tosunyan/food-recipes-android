plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.tosunyan.foodrecipes.common"
    compileSdk = 36

    defaultConfig {
        minSdk = 26
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = "21"
    }

    buildFeatures {
        buildConfig = true
    }

    testFixtures {
        enable = true
    }
}

dependencies {
    implementation(projects.core.di)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization)
    implementation(libs.core.ktx)

    testImplementation(libs.test.kotlin)
    testImplementation(testFixtures(projects.core.common))
    testFixturesApi(libs.test.kotlin)
    testFixturesApi(libs.test.coroutines)
    testFixturesApi(libs.test.mockk)
}