package dev.remodded.recore.sponge_api7

import dev.remodded.recore.api.ReCorePlatform
import dev.remodded.recore.api.lib.LibraryLoader
import dev.remodded.recore.api.platform.Platform
import dev.remodded.recore.api.platform.PlatformInfo
import dev.remodded.recore.common.ReCoreImpl
import org.spongepowered.api.Sponge

class ReCoreSpongePlatform(
    private val libraryLoader: LibraryLoader,
) : ReCorePlatform {

    init {
        ReCoreImpl.init(this)
    }

    override fun getLibraryLoader() = libraryLoader

    override fun getPlatformInfo(): PlatformInfo {
        val serverPlatform = Sponge.getPlatform()
        val spongeContainer = serverPlatform.getContainer(org.spongepowered.api.Platform.Component.IMPLEMENTATION)

        return PlatformInfo(
            Platform.SPONGE_API7,
            spongeContainer.name,
            spongeContainer.version.toString(),
            serverPlatform.minecraftVersion.name,
            Sponge.getConfigManager().getSharedConfig(spongeContainer).directory,
        )
    }
}
