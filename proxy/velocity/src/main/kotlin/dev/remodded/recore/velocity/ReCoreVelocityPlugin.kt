package dev.remodded.recore.velocity

import com.google.inject.Inject
import com.velocitypowered.api.proxy.ProxyServer
import dev.remodded.recore.api.ReCorePlugin
import dev.remodded.recore.api.config.IConfigLoader
import dev.remodded.recore.api.lib.LibraryLoader
import dev.remodded.recore.api.platform.Platform
import dev.remodded.recore.api.platform.PlatformInfo
import dev.remodded.recore.common.config.ConfigLoader
import org.slf4j.LoggerFactory
import java.nio.file.Path

class ReCoreVelocityPlugin @Inject constructor(
    private val server: ProxyServer,
    private val plugin: ReCoreVelocity,
    private val libraryLoader: LibraryLoader
) : ReCorePlugin {

    private val logger = LoggerFactory.getLogger("ReCore")

    override fun getLibraryLoader(): LibraryLoader {
        return libraryLoader
    }

    override fun getPlatformInfo() = PlatformInfo(
        Platform.VELOCITY,
        server.version.name,
        server.version.version,
        "proxy"
    )

    override fun <T> getConfigLoader(pluginName: String, configClass: Class<T>): IConfigLoader<T> {
        val path = Path.of("./plugins")
        return ConfigLoader(path, pluginName, configClass)
    }
}
