
buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath (libs.navigation.safe.args.gradle.plugin)
        classpath (libs.google.services)
        classpath (libs.gradle)
        classpath (libs.firebase.crashlytics.gradle)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.dagger.hilt) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.navigation.safeargs) apply false
    alias(libs.plugins.google.service) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
}



