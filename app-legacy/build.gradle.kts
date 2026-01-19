plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "com.example.poc"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
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
    implementation(project(":common-kotlin"))
    implementation(project(":data:model"))
    implementation(project(":data:repository"))
    implementation(project(":data:api"))
    implementation(project(":feature-a"))
    implementation(project(":feature-b"))
    implementation(project(":feature-c"))

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.android)

    // AndroidX
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.livedata)

    // Material
    implementation(libs.google.material)

    // Dagger
    implementation(libs.dagger.core)
    implementation(libs.dagger.android)
    implementation(libs.dagger.android.support)
    ksp(libs.dagger.compiler)
    ksp(libs.dagger.android.processor)

    // Network (for modules)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging)
}
