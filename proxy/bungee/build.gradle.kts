import dev.remodded.regradle.getPluginProps
import dev.remodded.regradle.markAsBuildTarget
import dev.remodded.regradle.markAsNeedShadow

markAsBuildTarget()
markAsNeedShadow()

repositories {
    maven("https://repo.remodded.dev/repository/paper/")
}

dependencies {
    api(project("::proxy"))
    compileOnly("io.github.waterfallmc:waterfall-api:1.20-R0.2-SNAPSHOT")
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
