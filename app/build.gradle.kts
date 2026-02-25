plugins {
    alias(libs.plugins.ioffeivan.android.application)
    alias(libs.plugins.ioffeivan.compose)
    alias(libs.plugins.ioffeivan.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.ioffeivan.mobilelibrary"

    defaultConfig {
        applicationId = "com.ioffeivan.mobilelibrary"

        val mobileLibraryVersion = libs.versions.mobileLibraryVersion.get()
        versionCode = generateVersionCode(mobileLibraryVersion)
        versionName = mobileLibraryVersion

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
    implementation(projects.core.designsystem)
    implementation(projects.core.ui)

    implementation(projects.feature.signIn)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation.compose)
}

fun generateVersionCode(version: String): Int {
    val versionRegex = Regex("""^(\d+)\.(\d+)\.(\d+)$""")
    val match =
        versionRegex.matchEntire(version)
            ?: throw IllegalArgumentException("Invalid version format: $version. Expected X.Y.Z")

    val (major, minor, patch) = match.destructured.toList().map { it.toInt() }

    require(minor <= 99) { "Minor version must be <= 99" }
    require(patch <= 99) { "Patch version must be <= 99" }

    return major * 10_000 + minor * 100 + patch
}
