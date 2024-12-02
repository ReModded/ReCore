package dev.remodded.recore.paper

import dev.remodded.recore.api.Server
import dev.remodded.recore.api.lib.LibraryLoader
import dev.remodded.recore.api.platform.Platform
import dev.remodded.recore.api.platform.PlatformInfo
import dev.remodded.recore.paper.command.PaperCommandManager
import org.bukkit.Bukkit

class PaperServer(
    override val libraryLoader: LibraryLoader,
) : Server {

    override val commandManager = PaperCommandManager()
    override val playerManager = PaperPlayerManager()

    override val platformInfo = Bukkit.getServer().let { server ->
        PlatformInfo(
            Platform.PAPER,
            server.name,
            server.version,
            server.minecraftVersion,
            Bukkit.getPluginsFolder().toPath(),
        )
    }
}
