plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.kzalink.pruebatecnicaigualadigital"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.kzalink.pruebatecnicaigualadigital"
        minSdk = 26
        targetSdk = 35
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Navegación
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    // RecyclerView
    implementation(libs.androidx.recyclerview)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // Paging
    implementation(libs.paging)

    // Corrutinas
    implementation(libs.coroutines)
}

kapt {
    correctErrorTypes true
}