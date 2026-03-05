plugins {
    alias(libs.plugins.ioffeivan.android.library)
    alias(libs.plugins.ioffeivan.hilt)
}

android {
    namespace = "com.ioffeivan.core.data"

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
    implementation(projects.core.common)
    implementation(projects.core.datastoreUser)
    implementation(projects.core.domain)
    implementation(projects.core.model)
    implementation(projects.core.network)

    androidTestImplementation(libs.androidx.test.runner)
}
