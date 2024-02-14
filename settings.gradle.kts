import java.net.URI

include(":feature:favorites")


include(":feature:find_people")


pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven {
            setUrl("https://jitpack.io")
        }

    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven {
            setUrl("https://jitpack.io")
        }
    }
}
rootProject.name = "App-Template-Compose"

include(":app")
include(":core:ui")
include(":core:navigation")
include(":core:domain")
include(":core:coroutines")

include(":feature:home")
include(":feature:onboarding")
include(":feature:subscription")
include(":feature:chat_list")
include(":feature:people_list")
include(":feature:user_profile")
include(":feature:people_profile")
include(":feature:people")
include(":core:di")
include(":feature:signin")
include(":feature:signup")
