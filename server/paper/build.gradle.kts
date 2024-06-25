
import dev.remodded.regradle.getPluginProps
import dev.remodded.regradle.markAsBuildTarget
import dev.remodded.regradle.markAsNeedShadow
import io.papermc.paperweight.userdev.ReobfArtifactConfiguration

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
    paperweight.paperDevBundle("1.21-R0.1-SNAPSHOT")
    api(project("::server"))
}

paperweight.reobfArtifactConfiguration = ReobfArtifactConfiguration.MOJANG_PRODUCTION

tasks {
    runServer {
        minecraftVersion("1.21")
    }

    // Configure reobfJar to run when invoking the build task
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
}

//paperPluginYaml {
//    val props = getPluginProps()
//
//    main.set(props["entry_point"] + "Platform")
//    bootstrapper = props["root_package"] + ".ReCoreBootstrapper"
//    authors.add(props["author"] ?: "ReModded Team")
//    apiVersion.set("1.20.6")
//}
