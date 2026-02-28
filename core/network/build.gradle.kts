plugins {
    alias(libs.plugins.ioffeivan.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.ioffeivan.core.network"

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
    implementation(libs.kotlinx.serialization.json)
}
