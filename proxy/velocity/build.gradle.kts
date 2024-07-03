import dev.remodded.regradle.getPluginProps
import dev.remodded.regradle.markAsBuildTarget
import dev.remodded.regradle.markAsNeedShadow

plugins {
    id("kotlin-kapt")
    id("xyz.jpenilla.run-velocity") version "2.3.0"
}

markAsBuildTarget()
markAsNeedShadow()

repositories {
    maven("https://repo.remodded.dev/repository/paper/")
    maven("https://repo.remodded.dev/repository/maven-public/")
}

dependencies {
    api(project("::proxy"))
    compileOnly("com.velocitypowered:velocity-api:3.3.0-SNAPSHOT")
    compileOnly("com.velocitypowered:velocity-proxy:3.3.0-SNAPSHOT")

    compileOnly("io.netty:netty-all:4.1.106.Final")

    kapt(annotationProcessor("com.velocitypowered:velocity-api:3.3.0-SNAPSHOT")!!)
}

val props = getPluginProps()

tasks {
    processResources {
        filteringCharset = Charsets.UTF_8.name()
        filter {
            var replaced = it
            props.forEach { (key, value) ->
                replaced = replaced.replace("@$key@", value)
            }
            return@filter replaced
        }
    }

    runVelocity {
        velocityVersion("3.3.0-SNAPSHOT")
    }
}
