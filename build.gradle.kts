plugins {
    id("com.android.application") version "8.5.2" apply false

    val kotlinVersion = "2.0.10"
    kotlin("android") version kotlinVersion apply false
    kotlin("plugin.compose") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("com.google.gms.google-services") version "4.3.13"
    id("com.google.devtools.ksp") version "2.0.10-1.0.24"
}