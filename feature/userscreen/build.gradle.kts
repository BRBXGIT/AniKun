plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    //Compose
    alias(libs.plugins.compose.compiler)
    //Nav
    alias(libs.plugins.kotlin.serialization)
    //Ksp
    alias(libs.plugins.ksp)
    //Hilt
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.example.userscreen"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    //Modules
    implementation(project(":core:designsystem"))
    implementation(project(":feature:mediascreen"))
    implementation(project(":core:data"))

    //Compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    //Material 3
    implementation(libs.androidx.material3)
    //Coil
    implementation(libs.coil.compose)
    //Nav
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
}