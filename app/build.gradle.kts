plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {

    namespace = "com.niraj.notificationdatacollector"

    compileSdk = 37

    defaultConfig {

        applicationId = "com.niraj.notificationdatacollector"

        minSdk = 26
        targetSdk = 37

        versionCode = 31
        versionName = "3.1"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        release {

            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {

        sourceCompatibility =
            JavaVersion.VERSION_11

        targetCompatibility =
            JavaVersion.VERSION_11
    }

    kotlinOptions {

        jvmTarget = "11"
    }
}

dependencies {

    // ------------------------------------------------
    // Core Android
    // ------------------------------------------------

    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.appcompat)

    implementation(libs.material)

    implementation(libs.androidx.activity.ktx)

    implementation(libs.androidx.constraintlayout)

    implementation(libs.androidx.recyclerview)

    // ------------------------------------------------
    // Lifecycle
    // ------------------------------------------------

    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.androidx.lifecycle.livedata.ktx)

    // ------------------------------------------------
    // Room
    // ------------------------------------------------

    implementation(libs.room.runtime)

    implementation(libs.room.ktx)

    ksp(libs.room.compiler)

    // ------------------------------------------------
    // WorkManager
    // ------------------------------------------------

    implementation(libs.androidx.work.runtime.ktx)

    // ------------------------------------------------
    // Coroutines
    // ------------------------------------------------

    implementation(libs.kotlinx.coroutines.android)

    // ------------------------------------------------
    // Unit Testing
    // ------------------------------------------------

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)

    androidTestImplementation(
        libs.androidx.espresso.core
    )
}