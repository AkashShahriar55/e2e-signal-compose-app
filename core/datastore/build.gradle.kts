plugins {
    id("core-module")
    alias(libs.plugins.nsa.android.hilt)
}

dependencies {
    api(projects.core.coroutines)
    api(libs.androidx.dataStore.core)
    api(projects.core.datastoreProto)
}