package dev.remodded.recore.paper

import dev.remodded.recore.api.ReCorePlatform
import dev.remodded.recore.api.lib.LibraryLoader
import dev.remodded.recore.api.platform.Platform
import dev.remodded.recore.api.platform.PlatformInfo
import dev.remodded.recore.common.ReCoreImpl
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class ReCorePaperPlatform(
    private val libraryLoader: LibraryLoader
) : JavaPlugin(), ReCorePlatform {

    override fun onEnable() {
        ReCoreImpl.init(this)
    }

    override fun getLibraryLoader() = libraryLoader

    override fun getPlatformInfo() = PlatformInfo(
        Platform.PAPER,
        server.name,
        server.version,
        server.minecraftVersion,
        Bukkit.getPluginsFolder().toPath(),
    )
}
