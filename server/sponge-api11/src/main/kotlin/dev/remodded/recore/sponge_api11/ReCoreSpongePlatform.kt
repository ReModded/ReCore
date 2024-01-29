package dev.remodded.recore.sponge_api11

import dev.remodded.recore.api.ReCorePlatform
import dev.remodded.recore.api.lib.LibraryLoader
import dev.remodded.recore.api.platform.Platform
import dev.remodded.recore.api.platform.PlatformInfo
import dev.remodded.recore.common.ReCoreImpl
import org.spongepowered.api.Sponge

class ReCoreSpongePlatform(
    private val libraryLoader: LibraryLoader
) : ReCorePlatform {

    init {
        ReCoreImpl.init(this)
    }

    override fun getLibraryLoader() = libraryLoader

    override fun getPlatformInfo(): PlatformInfo {
        val serverPlatform = Sponge.platform()
        val spongeContainer = serverPlatform.container(org.spongepowered.api.Platform.Component.IMPLEMENTATION)
        val meta = spongeContainer.metadata()

        return PlatformInfo(
            Platform.SPONGE_API11,
            meta.name().orElse("Sponge-API11 (Unknown)"),
            meta.version().toString(),
            serverPlatform.minecraftVersion().name(),
            Sponge.configManager().sharedConfig(spongeContainer).directory(),
        )
    }
}
