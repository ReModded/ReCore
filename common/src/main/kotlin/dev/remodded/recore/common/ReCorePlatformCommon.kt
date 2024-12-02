package dev.remodded.recore.common

import dev.remodded.recore.api.messaging.MessagingManager
import dev.remodded.recore.api.plugins.ReCorePlugin
import org.slf4j.Logger

interface ReCorePlatformCommon : ReCorePlugin {
    override val logger: Logger
        get() = ReCoreImpl.logger

    fun createChannelMessagingManager(): MessagingManager
}
