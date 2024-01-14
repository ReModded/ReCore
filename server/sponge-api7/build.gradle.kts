import dev.remodded.regradle.getPluginProps
import dev.remodded.regradle.markAsBuildTarget
import dev.remodded.regradle.markAsNeedShadow

markAsBuildTarget()
markAsNeedShadow()

repositories {
    maven("https://repo.remodded.dev/repository/sponge/")
}

dependencies {
    api(project("::server"))
    compileOnly("org.spongepowered:spongeapi:7.2.0")
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

kotlin {
    jvmToolchain(8)
}
