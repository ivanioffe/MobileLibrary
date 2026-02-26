plugins {
    alias(libs.plugins.ioffeivan.android.library)
}

android {
    namespace = "com.ioffeivan.core.presentation"

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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1,LICENSE.md,LICENSE-notice.md}"
        }
    }
    testFixtures {
        enable = true
    }
}

dependencies {
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    testFixturesImplementation(libs.kotlinx.coroutines.test)
    testFixturesImplementation(libs.test.junit5.api)
    testFixturesImplementation(platform(libs.test.junit5.bom))
}
