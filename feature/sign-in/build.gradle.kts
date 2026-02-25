plugins {
    alias(libs.plugins.ioffeivan.android.library)
    alias(libs.plugins.ioffeivan.hilt)
    alias(libs.plugins.secrets)
}

android {
    namespace = "com.ioffeivan.feature.sign_in"

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
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.datastoreAuth)
    implementation(projects.core.datastoreUser)
    implementation(projects.core.model)

    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.google.android.identity.googleid)
    implementation(libs.play.services.auth)
}
