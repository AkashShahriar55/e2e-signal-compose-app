plugins {
    id("core-module")
}
dependencies {
    implementation(libs.androidx.annotation.jvm)
    api(projects.core.coroutines)
    api(libs.bundles.compose)
}
