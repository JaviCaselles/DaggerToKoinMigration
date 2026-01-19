pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "poc-dagger-to-koin"

include(":app-gms")
include(":app-hms")
include(":app-legacy")
include(":common-kotlin")
include(":feature-a")
include(":feature-b")
include(":data:api")
include(":data:repository")
include(":data:model")
include(":feature-c")
