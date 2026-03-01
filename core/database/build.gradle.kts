plugins {
    alias(libs.plugins.ioffeivan.android.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

android {
    namespace = "com.ioffeivan.core.database"

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

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)

    ksp(libs.room.compiler)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.truth)
}
