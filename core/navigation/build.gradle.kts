@Suppress("DSL_SCOPE_VIOLATION")
plugins {
   id("core-compose-module")
}


dependencies {
    //Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(project(":feature:chat"))
    debugImplementation(libs.bundles.compose.debug)

    api(libs.navigation)

    api(projects.core.ui)
    api(projects.core.di)
    implementation(projects.core.domain)

    implementation(projects.feature.home)
    implementation(projects.feature.onboarding)
    implementation(projects.feature.subscription)
    implementation(projects.feature.chatList)
    implementation(projects.feature.people)
    implementation(projects.feature.userProfile)
    implementation(projects.feature.signin)
    implementation(projects.feature.signup)
    implementation(projects.feature.findPeople)
    implementation(projects.feature.favorites)

    implementation(libs.hilt.compose)
}