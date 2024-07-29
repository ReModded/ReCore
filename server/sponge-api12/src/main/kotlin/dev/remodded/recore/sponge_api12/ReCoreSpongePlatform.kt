package dev.remodded.recore.sponge_api12

import dev.remodded.recore.api.lib.LibraryLoader
import dev.remodded.recore.api.platform.Platform
import dev.remodded.recore.api.platform.PlatformInfo
import dev.remodded.recore.common.ReCoreImpl
import dev.remodded.recore.common.ReCorePlatformCommon
import dev.remodded.recore.sponge_api12.command.ReCoreSpongeCommandManager
import dev.remodded.recore.sponge_api12.messaging.channel.SpongeChannelMessagingManager
import org.spongepowered.api.Sponge

class ReCoreSpongePlatform(
    override val libraryLoader: LibraryLoader
) : ReCorePlatformCommon {

    override fun createChannelMessagingManager() = SpongeChannelMessagingManager()

    override val commandManager = ReCoreSpongeCommandManager()
    override val platformInfo: PlatformInfo = getPlatformInfo()

    init {
        ReCoreImpl.init(this)
    }

    override fun getPluginInfo() = ReCoreSponge.INSTANCE.getPluginInfo()

    companion object {
        private fun getPlatformInfo(): PlatformInfo {
            val serverPlatform = Sponge.platform()
            val spongeContainer = serverPlatform.container(org.spongepowered.api.Platform.Component.IMPLEMENTATION)
            val meta = spongeContainer.metadata()

            return PlatformInfo(
                Platform.SPONGE_API12,
                meta.name().orElse("Sponge-API11 (Unknown)"),
                meta.javaClass.getMethod("version").invoke(meta).toString(),
                serverPlatform.minecraftVersion().name(),
                Sponge.configManager().sharedConfig(spongeContainer).directory(),
            )
        }
    }
}
