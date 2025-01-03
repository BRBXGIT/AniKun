pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "AniList"
include(":app")
include(":core")
include(":feature")
include(":core:designsystem")
include(":core:common")
include(":core:data")
include(":feature:navbarscreens")
include(":feature:auth")
include(":feature:mediascreen")
include(":feature:settingsscreen")
include(":feature:userscreen")
