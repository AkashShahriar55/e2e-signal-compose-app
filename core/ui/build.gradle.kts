plugins {
    id("core-compose-module")
}
dependencies {
    api(libs.bundles.lyfecycle)
    api(libs.coil)
    api(libs.lottie)
    implementation(libs.fontawsome)
    api(projects.core.domain)
}
