plugins {
    alias(libs.plugins.ioffeivan.android.application)
    alias(libs.plugins.ioffeivan.compose)
}

android {
    namespace = "com.ioffeivan.mobilelibrary"

    defaultConfig {
        applicationId = "com.ioffeivan.mobilelibrary"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
}
