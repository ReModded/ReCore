import dev.remodded.regradle.getPluginProps
import dev.remodded.regradle.getProjectSuffix
import dev.remodded.regradle.markAsBuildTarget
import dev.remodded.regradle.markAsNeedShadow

plugins {
    id("io.papermc.paperweight.userdev") version "1.7.1"
    id("xyz.jpenilla.run-paper") version "2.3.0"
}

markAsBuildTarget()
markAsNeedShadow()

repositories {
    maven("https://repo.remodded.dev/repository/paper/")
}

dependencies {
    paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")
    api(project("::server"))
}

tasks {
    runServer {
        minecraftVersion("1.20.4")
    }
    // Configure reobfJar to run when invoking the build task
    assemble {
        dependsOn(reobfJar)
    }

    processResources {
        val props = getPluginProps()
        filteringCharset = Charsets.UTF_8.name()
        filter {
            var replaced = it
            props.forEach { (key, value) ->
                replaced = replaced.replace("@$key@", value)
            }
            return@filter replaced
        }
    }

    reobfJar {
        val props = getPluginProps()

        jar.get().archiveClassifier.set("clean")
        jar.get().archiveAppendix.set(project.getProjectSuffix())
        jar.get().archiveBaseName.set(props["name"])

        shadowJar.get().archiveClassifier.set("")
        shadowJar.get().archiveAppendix.set(project.getProjectSuffix())
        shadowJar.get().archiveBaseName.set(props["name"])

        outputJar.set(project.buildDir.resolve("libs").resolve(shadowJar.get().archiveFile.get().asFile.name))
    }
}
