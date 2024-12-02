package dev.remodded.recore.sponge_api12

import dev.remodded.recore.api.Server
import dev.remodded.recore.api.lib.LibraryLoader
import dev.remodded.recore.api.platform.Platform
import dev.remodded.recore.api.platform.PlatformInfo
import dev.remodded.recore.sponge_api12.command.ReCoreSpongeCommandManager
import org.spongepowered.api.Sponge

class SpongeServer(
    override val libraryLoader: LibraryLoader
) : Server {
    override val platformInfo: PlatformInfo = getPlatformInfo()

    override val commandManager = ReCoreSpongeCommandManager()

    companion object {
        private fun getPlatformInfo(): PlatformInfo {
            val serverPlatform = Sponge.platform()
            val spongeContainer = serverPlatform.container(org.spongepowered.api.Platform.Component.IMPLEMENTATION)
            val meta = spongeContainer.metadata()

            return PlatformInfo(
                Platform.SPONGE_API12,
                meta.name().orElse("Sponge-API11 (Unknown)"),
                meta.version().toString(),
                serverPlatform.minecraftVersion().name(),
                Sponge.configManager().sharedConfig(spongeContainer).directory(),
            )
        }
    }

}
