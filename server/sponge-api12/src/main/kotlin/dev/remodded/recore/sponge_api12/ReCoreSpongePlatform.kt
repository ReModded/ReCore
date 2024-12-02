package dev.remodded.recore.sponge_api12

import dev.remodded.recore.api.lib.LibraryLoader
import dev.remodded.recore.common.ReCoreImpl
import dev.remodded.recore.common.ReCorePlatformCommon
import dev.remodded.recore.sponge_api12.messaging.channel.SpongeChannelMessagingManager

class ReCoreSpongePlatform(
    libraryLoader: LibraryLoader
) : ReCorePlatformCommon {

    override fun createChannelMessagingManager() = SpongeChannelMessagingManager()

    init {
        ReCoreImpl.init(SpongeServer(libraryLoader), this)
    }

    override fun getPluginInfo() = ReCoreSponge.INSTANCE.getPluginInfo()
}
