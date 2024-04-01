pluginManagement {
    includeBuild("build-logic")
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

rootProject.name = "Czech Fool App"
include(":app")
include(":core:database")
include(":core:model")
include(":core:data")
include(":core:domain")
include(":feature:gameshistory")
include(":core:designsystem")
include(":feature:gameoptions")
include(":feature:nameinput")
include(":feature:game")
