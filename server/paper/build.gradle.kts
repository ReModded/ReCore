
import dev.remodded.regradle.getPluginProps
import dev.remodded.regradle.markAsBuildTarget
import dev.remodded.regradle.markAsNeedShadow
import io.papermc.paperweight.userdev.ReobfArtifactConfiguration

plugins {
    id("io.papermc.paperweight.userdev") version "1.7.1"
    id("xyz.jpenilla.run-paper") version "2.3.0"
    id("xyz.jpenilla.resource-factory-paper-convention") version "1.1.1"
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
}

paperPluginYaml {
    val props = getPluginProps()

    name.set(props.name)
    version.set(props.version)
    description.set(props.description)
    authors.add(props.author)

    apiVersion.set("1.21")
    main.set("${props.entryPoint}Platform")
    bootstrapper = "${props.entryPoint}Bootstrapper"
}
