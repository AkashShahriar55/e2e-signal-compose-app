plugins {
    id("core-module")
    alias(libs.plugins.nsa.android.hilt)
}


dependencies {
    api(libs.coroutines.android)
    api(projects.core.network)
    api(projects.core.coroutines)
}