plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.lolbet"
    compileSdk {
        version = release(36)
    }

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.lolbet"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Nécessaire pour le desugaring sur API 24
        multiDexEnabled = true
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
        // Active le support java.time sur les anciens Android
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // --- DESUGARING (Pour utiliser java.time sur API 24) ---
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")

    // On utilise soit les versions "libs.xxx" (Version Catalog), soit les strings directes, mais pas les deux.
    // Ici, je garde les versions les plus stables :
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Navigation (Nettoyé des doublons)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Fragment KTX est déjà inclus souvent via navigation, mais on peut le garder si besoin
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    // Tests
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}