package dev.remodded.recore.bungee

import dev.remodded.recore.api.ReCorePlugin
import dev.remodded.recore.api.lib.LibraryLoader
import dev.remodded.recore.api.platform.Platform
import dev.remodded.recore.api.platform.PlatformInfo
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Plugin
import org.slf4j.LoggerFactory

class ReCoreBungeePlugin(
    private val plugin: Plugin,
    private val libraryLoader: LibraryLoader
) : ReCorePlugin {

    private val logger = LoggerFactory.getLogger("ReCore")

    override fun getLibraryLoader(): LibraryLoader {
        return libraryLoader
    }

    override fun getPlatformInfo() = PlatformInfo(
        Platform.BUNGEE,
        ProxyServer.getInstance().name,
        ProxyServer.getInstance().version,
        "proxy"
    )
}
