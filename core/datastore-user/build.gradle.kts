plugins {
    alias(libs.plugins.ioffeivan.datastore)
}

android {
    namespace = "com.ioffeivan.core.datastore_user"

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
