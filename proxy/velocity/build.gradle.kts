import dev.remodded.regradle.getPluginProps
import dev.remodded.regradle.markAsBuildTarget
import dev.remodded.regradle.markAsNeedShadow

plugins {
    id("xyz.jpenilla.run-velocity") version "2.3.0"
    id("xyz.jpenilla.resource-factory-velocity-convention") version "1.1.2-SNAPSHOT"
}

markAsBuildTarget()
markAsNeedShadow()

repositories {
    maven("https://repo.remodded.dev/repository/PaperMC/")
    maven("https://repo.remodded.dev/repository/maven-public/")
}

dependencies {
    api(project("::proxy"))
    compileOnly("com.velocitypowered:velocity-api:3.3.0-SNAPSHOT")
    compileOnly("com.velocitypowered:velocity-proxy:3.3.0-SNAPSHOT")

    compileOnly("io.netty:netty-all:4.1.106.Final")
}

tasks {
    runVelocity {
        velocityVersion("3.3.0-SNAPSHOT")
    }
}

velocityPluginJson {
    val props = getPluginProps()

    id.set(props.id)
    name.set(props.name)
    version.set(props.version)

    url.set(props.url)
    authors.add(props.author)
    description.set(props.description)

    main.set(props.entryPoint)

    dependency("recore", false)
}
