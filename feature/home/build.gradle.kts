plugins {
    alias(libs.plugins.ioffeivan.android.library)
}

android {
    namespace = "com.ioffeivan.feature.home"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
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
    androidTestImplementation(libs.androidx.test.runner)
}
