package dev.remodded.recore.sponge_api7

import dev.remodded.recore.api.ReCorePlugin
import dev.remodded.recore.api.lib.LibraryLoader
import dev.remodded.recore.api.platform.Platform
import dev.remodded.recore.api.platform.PlatformInfo
import org.slf4j.LoggerFactory
import org.spongepowered.api.Sponge

class ReCoreSpongePlugin(
    private val libraryLoader: LibraryLoader
) : ReCorePlugin {

    private val logger = LoggerFactory.getLogger("ReCore")

    override fun getLibraryLoader(): LibraryLoader {
        return libraryLoader
    }

    override fun getPlatformInfo(): PlatformInfo {
        val serverPlatform = Sponge.getPlatform()
        val spongeContainer = serverPlatform.getContainer(org.spongepowered.api.Platform.Component.IMPLEMENTATION)

        return PlatformInfo(
            Platform.SPONGE_API7,
            spongeContainer.name,
            spongeContainer.version.toString(),
            serverPlatform.minecraftVersion.name
        )
    }
}
