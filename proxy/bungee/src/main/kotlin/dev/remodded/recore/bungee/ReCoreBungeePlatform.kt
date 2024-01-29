package dev.remodded.recore.bungee

import dev.remodded.recore.api.ReCorePlatform
import dev.remodded.recore.api.lib.LibraryLoader
import dev.remodded.recore.api.platform.Platform
import dev.remodded.recore.api.platform.PlatformInfo
import dev.remodded.recore.common.ReCoreImpl
import net.md_5.bungee.api.ProxyServer

class ReCoreBungeePlatform(
    private val libraryLoader: LibraryLoader
) : ReCorePlatform {

    init {
        ReCoreImpl.init(this)
    }

    override fun getLibraryLoader() = libraryLoader

    override fun getPlatformInfo() = PlatformInfo(
        Platform.BUNGEE,
        ProxyServer.getInstance().name,
        ProxyServer.getInstance().version,
        "proxy",
        ProxyServer.getInstance().pluginsFolder.toPath(),
    )
}
