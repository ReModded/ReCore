package dev.remodded.recore.common.messaging.channel

import dev.remodded.recore.api.messaging.MessagingChannelType
import dev.remodded.recore.common.Constants
import dev.remodded.recore.common.messaging.CommonMessagingManager
import java.util.*

abstract class CommonChannelMessagingManager : CommonMessagingManager() {
    override val type = MessagingChannelType.CHANNELS


    companion object {
        const val CHANNEL_NAMESPACE = Constants.ID
        const val CHANNEL_NAME = "messaging"
        const val CHANNEL_ID = "$CHANNEL_NAMESPACE:$CHANNEL_NAME"

        const val FAKE_PLAYER_NAME = "DummyConnection"
        val FAKE_PLAYER_UUID = UUID(0, 4)
    }
}
