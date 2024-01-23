package dev.remodded.recore.sponge_api11

import dev.remodded.recore.api.ReCorePlugin
import dev.remodded.recore.api.config.IConfigLoader
import dev.remodded.recore.api.lib.LibraryLoader
import dev.remodded.recore.api.platform.Platform
import dev.remodded.recore.api.platform.PlatformInfo
import dev.remodded.recore.common.config.ConfigLoader
import org.slf4j.LoggerFactory
import org.spongepowered.api.Sponge
import java.nio.file.Path

class ReCoreSpongePlugin(
    private val libraryLoader: LibraryLoader
) : ReCorePlugin {

    private val logger = LoggerFactory.getLogger("ReCore")
    override fun getLibraryLoader(): LibraryLoader {
        return libraryLoader
    }

    override fun getPlatformInfo(): PlatformInfo {
        val serverPlatform = Sponge.platform()
        val spongeContainer =
            serverPlatform.container(org.spongepowered.api.Platform.Component.IMPLEMENTATION).metadata()

        return PlatformInfo(
            Platform.SPONGE_API11,
            spongeContainer.name().orElse("Sponge-API11 (Unknown)"),
            spongeContainer.version().toString(),
            serverPlatform.minecraftVersion().name()
        )
    }

    override fun <T> getConfigLoader(pluginName: String, configClass: Class<T>): IConfigLoader<T> {
        val path = Path.of("./config")
        return ConfigLoader(path, pluginName, configClass)
    }
}
