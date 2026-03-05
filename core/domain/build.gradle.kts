plugins {
    alias(libs.plugins.ioffeivan.jvm.library)
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.model)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.javax.inject)
}
