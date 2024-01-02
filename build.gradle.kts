
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
    id("com.android.application") version "8.2.0" apply false
    id("com.android.library") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("com.google.dagger.hilt.android") version "2.49" apply false
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false
}



