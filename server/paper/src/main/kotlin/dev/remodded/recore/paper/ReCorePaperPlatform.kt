package dev.remodded.recore.paper

import dev.remodded.recore.api.ReCorePlatform
import dev.remodded.recore.api.lib.LibraryLoader
import dev.remodded.recore.api.platform.Platform
import dev.remodded.recore.api.platform.PlatformInfo
import dev.remodded.recore.common.PluginInfo
import dev.remodded.recore.common.ReCoreImpl
import dev.remodded.recore.paper.command.PaperCommandManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class ReCorePaperPlatform(
    override val libraryLoader: LibraryLoader
) : JavaPlugin(), ReCorePlatform {

    override val commandManager = PaperCommandManager()
    override val platformInfo = PlatformInfo(
        Platform.PAPER,
        server.name,
        server.version,
        server.minecraftVersion,
        Bukkit.getPluginsFolder().toPath(),
    )

    override fun onEnable() {
        PluginInfo.mainInstance = this
        ReCoreImpl.init(this)
    }
}
