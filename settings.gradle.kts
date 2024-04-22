pluginManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://repo.maven.apache.org/maven2/")
        }
        maven {
            url = uri("https://jitpack.io/")
        }
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

rootProject.name = "Proyecto Final"
include(":app")
 