package dev.remodded.recore.bungee

import dev.remodded.recore.api.ReCorePlatform
import dev.remodded.recore.api.lib.LibraryLoader
import dev.remodded.recore.api.platform.Platform
import dev.remodded.recore.api.platform.PlatformInfo
import dev.remodded.recore.common.ReCoreImpl
import net.md_5.bungee.api.ProxyServer

class ReCoreBungeePlatform(
    override val libraryLoader: LibraryLoader
) : ReCorePlatform {

    override val platformInfo = PlatformInfo(
        Platform.BUNGEE,
        ProxyServer.getInstance().name,
        ProxyServer.getInstance().version,
        "proxy",
        ProxyServer.getInstance().pluginsFolder.toPath(),
    )

    init {
        ReCoreImpl.init(this)
    }
}
