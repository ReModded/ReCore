package dev.remodded.recore.paper

import dev.remodded.recore.api.ReCore
import dev.remodded.recore.api.data.additional.AdditionalDataManager
import dev.remodded.recore.api.lib.LibraryLoader
import dev.remodded.recore.api.plugins.PluginInfo
import dev.remodded.recore.api.service.provide
import dev.remodded.recore.common.Constants
import dev.remodded.recore.common.ReCoreImpl
import dev.remodded.recore.common.ReCorePlatformCommon
import dev.remodded.recore.paper.data.additional.PaperAdditionalDataManager
import dev.remodded.recore.paper.messaging.channel.PaperChannelMessagingManager
import org.bukkit.plugin.java.JavaPlugin

class ReCorePaperPlatform(
    val libraryLoader: LibraryLoader,
) : JavaPlugin(), ReCorePlatformCommon {

    companion object {
        lateinit var INSTANCE: ReCorePaperPlatform
    }

    init {
        INSTANCE = this
    }

    override fun createChannelMessagingManager() = PaperChannelMessagingManager()

    override fun onEnable() {
        ReCoreImpl.init(PaperServer(libraryLoader), this)

        ReCore.INSTANCE.serviceProvider.provide<AdditionalDataManager, PaperAdditionalDataManager>(ReCore.INSTANCE)
    }

    override fun getPluginInfo(): PluginInfo {
        return PluginInfo(
            Constants.ID,
            Constants.NAME,
            Constants.VERSION,
            this,
        )
    }
}
