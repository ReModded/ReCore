pluginManagement {
    repositories {
        maven("https://repo.remodded.dev/repository/fabric/") {
            name = "Remodded Fabric proxy"
        }
        maven("https://repo.remodded.dev/repository/sponge/") {
            name = "Remodded Sponge proxy"
        }
        maven("https://repo.remodded.dev/repository/maven-snapshots/") {
            name = "Remodded Snapshots"
        }
        mavenCentral()
        gradlePluginPortal()
        maven("https://repo.spongepowered.org/repository/maven-public/") {
            name = "sponge Official"
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "ReCore"

val modules = arrayListOf(
    "api",
    "common",

    "server:paper",
    "server:sponge-api8",
    "server:sponge-api11",

    "proxy:velocity",
)

modules.forEach { module ->
    include(":$module")
    val project = project(":$module")
    project.name = module.substring(module.lastIndexOf(':') + 1)
    project.projectDir = file(module.replace(":", "/"))
}
