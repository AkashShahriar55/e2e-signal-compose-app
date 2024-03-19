plugins {
    id("feature-module")
    alias(libs.plugins.nsa.android.hilt)
}


dependencies {
    api(projects.core.domain)
}

