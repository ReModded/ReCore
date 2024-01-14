package dev.remodded.recore.velocity

import com.google.inject.Inject
import com.velocitypowered.api.proxy.ProxyServer
import dev.remodded.recore.api.ReCorePlugin
import dev.remodded.recore.api.lib.LibraryLoader
import dev.remodded.recore.api.platform.Platform
import dev.remodded.recore.api.platform.PlatformInfo
import org.slf4j.LoggerFactory

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
}
