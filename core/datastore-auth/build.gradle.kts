plugins {
    alias(libs.plugins.ioffeivan.datastore)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.ioffeivan.core.datastore_auth"

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
