pluginManagement {
    repositories {
        mavenLocal() // For ReGradle development
        maven("https://repo.remodded.dev/repository/fabric/") {
            name = "Remodded Fabric proxy"
        }
        maven("https://repo.remodded.dev/repository/sponge/") {
            name = "Remodded Sponge proxy"
        }
        maven("https://repo.remodded.dev/repository/maven-public/") {
            name = "Remodded Maven"
        }
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = settings.ext["\$name"] as String

val modules = arrayListOf(
    "api",
    "common",

    "server:paper",
    "server:sponge-api12",

    "proxy:velocity",
)

modules.forEach { module ->
    include(":$module")
    val project = project(":$module")
    project.name = module.substring(module.lastIndexOf(':') + 1)
    project.projectDir = file(module.replace(":", "/"))
}
