plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")

    // For Hilt
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    // Navigation using: https://github.com/raamcosta/compose-destinations
    id("com.google.devtools.ksp") version "1.8.10-1.0.9"
}

android {
    namespace = "cz.mendelu.pef.xmichl.bookaroo"
    compileSdk = 34

    defaultConfig {
        applicationId = "cz.mendelu.pef.xmichl.bookaroo"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Icons
    implementation("androidx.compose.material:material-icons-extended:1.4.2")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.cardview:cardview:1.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Map
    implementation("com.google.maps.android:maps-compose:3.1.1")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("androidx.compose.foundation:foundation:1.6.0-alpha07")
    implementation("com.google.maps.android:android-maps-utils:3.5.3")
    implementation("com.google.maps.android:maps-compose-widgets:3.1.1")
    implementation("com.google.maps.android:maps-compose-utils:3.1.1")



    // Room
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("androidx.room:room-ktx:2.4.3")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    kapt("androidx.room:room-compiler:2.4.3")
    implementation("androidx.room:room-runtime:2.4.3")
    // End room

    // Hilt
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Navigation
    implementation("io.github.raamcosta.compose-destinations:animations-core:1.8.42-beta")
    ksp("io.github.raamcosta.compose-destinations:ksp:1.8.42-beta")


    // Moshi for json converter
    implementation("com.squareup.moshi:moshi:1.14.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")


}
// For Hilt
kapt {
    correctErrorTypes = true
}
