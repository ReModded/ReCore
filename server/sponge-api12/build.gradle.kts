import dev.remodded.regradle.getPluginProps
import dev.remodded.regradle.markAsBuildTarget
import dev.remodded.regradle.markAsNeedShadow
import org.spongepowered.gradle.plugin.config.PluginLoaders
import org.spongepowered.gradle.vanilla.repository.MinecraftPlatform
import org.spongepowered.plugin.metadata.model.PluginDependency

plugins {
    id("org.spongepowered.gradle.plugin") version "2.2.1-SNAPSHOT"
    id("org.spongepowered.gradle.vanilla") version "0.2.1-SNAPSHOT"
}

markAsBuildTarget()
markAsNeedShadow()

repositories {
    maven("https://repo.remodded.dev/repository/Sponge/")
}

dependencies {
    api(project("::server"))
    implementation("org.spongepowered:sponge:1.21-12.0.0-SNAPSHOT")

    compileOnly("org.spongepowered:mixin:0.8.7-SNAPSHOT")
}

val props = getPluginProps()

minecraft {
    version("1.21")
    platform(MinecraftPlatform.SERVER)
}

sponge {
    apiVersion("12.0.0-SNAPSHOT")
    license("MIT")
    loader {
        name(PluginLoaders.JAVA_PLAIN)
        version("1.0")
    }
    plugin(props.id) {
        displayName(props.name)
        entrypoint(props.entryPoint)
        description(props.description)
        links {
            homepage(props.url)
            source(props.urlSrc)
            issues(props.urlIssues)
        }
        contributor(props.author) {}
        dependency("spongeapi") {
            loadOrder(PluginDependency.LoadOrder.AFTER)
            optional(false)
        }
    }
}

tasks {
    jar {
        manifest {
            attributes(
                "MixinConfigs" to "mixins.recore.json"
            )
        }
    }
}
