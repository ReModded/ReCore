package dev.remodded.recore.test

import dev.remodded.recore.api.messaging.MessageChannel
import dev.remodded.recore.api.messaging.MessagingChannelType
import dev.remodded.recore.api.messaging.MessagingManager
import dev.remodded.recore.api.plugins.PluginInfo
import dev.remodded.recore.common.Constants
import dev.remodded.recore.common.ReCorePlatformCommon

class ReCoreTestPlatform : ReCorePlatformCommon {
    override fun createChannelMessagingManager(): MessagingManager {
        return object : MessagingManager {
            override val type: MessagingChannelType
                get() = TODO("Not yet implemented")

            override fun <T> getChannel(
                channel: String,
                clazz: Class<T>
            ): MessageChannel<T> {
                TODO("Not yet implemented")
            }
        }
    }

    override fun getPluginInfo() = PluginInfo(
        Constants.ID,
        Constants.NAME,
        Constants.VERSION,
        this,
    )
}
