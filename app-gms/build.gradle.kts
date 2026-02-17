plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.poc.gms"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.poc.gms"
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
    implementation(project(":feature-c"))
    implementation(project(":data:repository"))
    implementation(project(":data:model"))
    implementation(project(":data:api"))

    // Koin
    implementation(libs.koin.android)
}
