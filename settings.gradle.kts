pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
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
    "server:sponge-api7",
    "server:sponge-api11",

    "proxy:velocity",
    "proxy:bungee",
)

modules.forEach { module ->
    include(":$module")
    val project = project(":$module")
    project.name = module.substring(module.lastIndexOf(':') + 1)
    project.projectDir = file(module.replace(":", "/"))
}
