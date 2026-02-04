plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // Apply the Hilt plugin here (no version or apply false)
    id("com.google.dagger.hilt.android")

    // 2. Apply the KAPT plugin here (no version or apply false)
    id("kotlin-kapt")
}

android {
    namespace = "link.phoenixwork.waterlevelmanager"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "link.phoenixwork.waterlevelmanager"
        minSdk = 35
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.material3)
    // Define local variables for cleaner version access
    val hilt_version = libs.versions.hilt.get();
    val hilt_compose_version = libs.versions.hiltNavigationCompose.get()
    // The AndroidX Hilt Compiler version usually matches the navigation-compose version
    val androidx_hilt_compiler_version = hilt_compose_version

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-compiler:$hilt_version") // or ksp()

    // Hilt Extension for Jetpack Compose (for hiltViewModel())
    implementation("androidx.hilt:hilt-navigation-compose:$hilt_compose_version")

    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Optional: AndroidX Hilt Compiler (needed for @HiltViewModel if hilt-compiler doesn't cover it)
    kapt("androidx.hilt:hilt-compiler:$androidx_hilt_compiler_version") // or ksp()


    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
}