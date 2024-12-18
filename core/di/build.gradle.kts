plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    api(project.dependencies.platform(libs.koin.bom))
    api(libs.koin.core)
    api(libs.koin.android)
    api(libs.koin.compose)
    api(libs.koin.compose.viewmodel)
}