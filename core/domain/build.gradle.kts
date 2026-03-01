plugins {
    alias(libs.plugins.ioffeivan.jvm.library)
}

dependencies {
    implementation(projects.core.common)

    implementation(libs.kotlinx.coroutines.core)
}
