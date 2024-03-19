plugins {
    id("feature-module")
    alias(libs.plugins.nsa.android.hilt)
}

dependencies {
    api(projects.core.domain)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.play.service)
    implementation(libs.androidx.core.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
}
