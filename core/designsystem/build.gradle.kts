plugins {
    alias(libs.plugins.ioffeivan.android.library)
    alias(libs.plugins.ioffeivan.compose)
}

android {
    namespace = "com.ioffeivan.core.designsystem"

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
