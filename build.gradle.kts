buildscript {
    dependencies {
        classpath(libs.secrets.gradlePlugin)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.secrets) apply false
}