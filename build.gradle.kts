// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies {
//        classpath("com.google.protobuf:protobuf-gradle-plugin:0.9.0")
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
    }
}

plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    // Hilt
    id("com.google.dagger.hilt.android") version "2.44" apply false
    // Realm
    id("io.realm.kotlin") version "1.11.0" apply false
    // Secrets
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1"
}

secrets {
    // Optionally specify a different file name containing your secrets.
    // The plugin defaults to "local.properties"
    propertiesFileName = "local.properties"

    // A properties file containing default secret values. This file can be
    // checked in version control.
    defaultPropertiesFileName = "local.defaults.properties"

    // Configure which keys should be ignored by the plugin by providing regular expressions.
    // "sdk.dir" is ignored by default.
    ignoreList.add("sdk.*")       // Ignore all keys matching the regexp "sdk.*"
}
