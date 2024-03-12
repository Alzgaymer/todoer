plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    kotlin("kapt")
    alias(libs.plugins.daggerHilt)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.todoer.wear"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.todoer.wear"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.9"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.activity.compose)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.play.services.wearable)
    implementation(platform(libs.compose.bom))
    implementation(libs.navigation.runtime.ktx)
    implementation(libs.firebase.database)
    implementation(platform("com.google.firebase:firebase-bom:32.7.3"))
    implementation(libs.firebase.firestore)
    implementation(libs.com.google.firebase.firebase.auth)
    implementation(libs.ui)
    implementation(libs.ui.tooling.preview)
    implementation(libs.compose.material)
    implementation(libs.compose.foundation)
    implementation(libs.activity.compose)
    implementation(libs.core.splashscreen)
    implementation(libs.play.services.auth)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    implementation(libs.runtime)
    implementation(libs.lifecycle.runtime.compose)
    //dependency for the navigation.
    implementation (libs.navigation.compose)

    implementation(libs.wear.compose.foundation)
    implementation(libs.wear.compose.material)
    implementation(libs.wear.compose.ui)
    implementation(libs.wear.compose.navigation)
    implementation(libs.horologist.compose.material)
    implementation(libs.horologist.compose.layout)
    implementation(libs.horologist.composables)

    implementation (libs.maps.compose)

    // Optionally, you can include the Compose utils library for Clustering,
    // Street View metadata checks, etc.
    implementation (libs.maps.compose.utils)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.play.services)
    implementation(libs.kotlinx.coroutines.core)

    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    kapt(libs.hilt.compiler)
    implementation (libs.hilt.navigation.compose)
}