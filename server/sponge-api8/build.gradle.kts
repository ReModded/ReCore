import dev.remodded.regradle.getPluginProps
import dev.remodded.regradle.markAsBuildTarget
import dev.remodded.regradle.markAsNeedShadow
import org.spongepowered.gradle.plugin.config.PluginLoaders
import org.spongepowered.plugin.metadata.model.PluginDependency

plugins {
    id("org.spongepowered.gradle.plugin") version "2.1.1"
}

markAsBuildTarget()
markAsNeedShadow()

repositories {
    maven("https://repo.remodded.dev/repository/sponge/")
}

dependencies {
    api(project("::server"))
    compileOnly("cpw.mods:modlauncher:8.1.3")
}

val props = getPluginProps()

sponge {
    apiVersion("8.0.0-SNAPSHOT")
    license("MIT")
    loader {
        name(PluginLoaders.JAVA_PLAIN)
        version("1.0")
    }
    plugin(props["id"]) {
        displayName(props["name"])
        entrypoint(props["entry_point"])
        description(props["description"])
        links {
            homepage(props["url"])
            source(props["src_url"])
            issues(props["issues_url"])
        }
        contributor(props["author"]) {}
        dependency("spongeapi") {
            loadOrder(PluginDependency.LoadOrder.AFTER)
            optional(false)
        }
    }
}

kotlin {
    jvmToolchain(8)
}
