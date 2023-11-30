// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies {
        classpath("com.google.protobuf:protobuf-gradle-plugin:0.8.19")
    }
}

plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    // Hilt
    id("com.google.dagger.hilt.android") version "2.44" apply false
    // Realm
    id("io.realm.kotlin") version "1.11.0" apply false
}
