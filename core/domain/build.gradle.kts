plugins {
    id("core-module")
    alias(libs.plugins.nsa.android.hilt)
}
dependencies {
    implementation(libs.androidx.annotation.jvm)
    api(projects.core.coroutines)
    api(projects.core.data)
    api(libs.bundles.compose)
}
