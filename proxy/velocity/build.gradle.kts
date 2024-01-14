import dev.remodded.regradle.getPluginProps
import dev.remodded.regradle.markAsBuildTarget
import dev.remodded.regradle.markAsNeedShadow

plugins {
    id("kotlin-kapt")
}

markAsBuildTarget()
markAsNeedShadow()

repositories {
    maven("https://repo.remodded.dev/repository/paper/")
}

dependencies {
    api(project("::proxy"))
    compileOnly("com.velocitypowered:velocity-api:3.2.0-SNAPSHOT")
//    kapt("com.velocitypowered:velocity-api:3.2.0-SNAPSHOT")
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
}
