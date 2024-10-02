plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.devtools.ksp")
    kotlin("kapt")
    alias(libs.plugins.googleGmsGoogleServices)
}

android {
    namespace = "com.petpath.walk"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.petpath.walk"
        minSdk = 30
        targetSdk = 34
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.room.runtime.v261)
    implementation(libs.androidx.room.ktx)
    implementation(libs.firebase.inappmessaging.display)
    implementation(libs.firebase.messaging.ktx)
    implementation(libs.accompanist.insets)
    implementation(libs.androidx.runtime.livedata)
    annotationProcessor(libs.androidx.room.compiler.v261)
    // To use Kotlin Symbol Processing (KSP)
    //ksp("androidx.room:room-compiler:2.5.0")
    //ksp ("androidx.room:room-compiler:$2.6.1")
    kapt(libs.androidx.room.compiler.v261)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.play.services.location)
    implementation(libs.accompanist.permissions)
    implementation(libs.androidx.lifecycle.service)
    implementation(libs.socket.io.client)
    implementation(libs.json)
    implementation(libs.navigation.compose)
    implementation(libs.coil.compose)
    implementation(libs.balloon)
    implementation(libs.balloon.compose)
    implementation(libs.material3)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}