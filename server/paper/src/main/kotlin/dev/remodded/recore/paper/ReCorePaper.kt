package dev.remodded.recore.paper

import dev.remodded.recore.api.ReCorePlugin
import dev.remodded.recore.api.config.IConfigLoader
import dev.remodded.recore.api.lib.LibraryLoader
import dev.remodded.recore.api.platform.Platform
import dev.remodded.recore.api.platform.PlatformInfo
import dev.remodded.recore.common.config.ConfigLoader
import org.bukkit.plugin.java.JavaPlugin
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.file.Path

class ReCorePaper(
    private val libraryLoader: LibraryLoader
) : JavaPlugin(), ReCorePlugin {

    private var logger: Logger = LoggerFactory.getLogger("ReCore")

    override fun onEnable() {
        logger.info("Loading...")
    }

    override fun getLibraryLoader(): LibraryLoader {
        return libraryLoader
    }

    override fun getPlatformInfo() = PlatformInfo(
        Platform.PAPER,
        server.name,
        server.version,
        server.minecraftVersion
    )

    override fun <T> getConfigLoader(pluginName: String, configClass: Class<T>): IConfigLoader<T> {
        val path = Path.of("./plugins")
        return ConfigLoader(path, pluginName, configClass)
    }
}
