plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "com.example.poc.hms"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.poc.hms"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":app-legacy"))
    implementation(project(":common-kotlin"))
    implementation(project(":feature-a"))
    implementation(project(":feature-b"))
    implementation(project(":data:repository"))
    implementation(project(":data:model"))
    implementation(project(":data:api"))

    // Network (needed for Dagger components extending modules in app-legacy)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging)

    // Dagger
    implementation(libs.dagger.core)
    implementation(libs.dagger.android)
    implementation(libs.dagger.android.support)
    ksp(libs.dagger.compiler)
    ksp(libs.dagger.android.processor)
    
    // Koin
    implementation(libs.koin.android)
}
