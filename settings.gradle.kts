
import dev.remodded.regradle.MCVersion
import dev.remodded.regradle.regradle
import dev.remodded.regradle.utils.Version

pluginManagement {
    repositories {
        mavenLocal() // For ReGradle development
        maven("https://repo.remodded.dev/repository/ReGradle/") {
            name = "Remodded Maven"
        }
    }
}

plugins {
    id("dev.remodded.regradle") version "1.0.0-SNAPSHOT"
}

regradle {
    mcVersion(MCVersion.V1_21_3)

    root("")

    api("api")
    common("common")
    server("server")
    proxy("proxy")

    paper("server:paper")

    sponge("server:sponge-api12", MCVersion.V1_21_1)

    velocity("proxy:velocity", Version(3, 3, 0, "SNAPSHOT"))
}
