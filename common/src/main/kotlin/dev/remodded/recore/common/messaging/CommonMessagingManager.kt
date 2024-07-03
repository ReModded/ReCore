package dev.remodded.recore.common.messaging

import dev.remodded.recore.api.messaging.MessageChannel
import dev.remodded.recore.api.messaging.MessagingManager

abstract class CommonMessagingManager : MessagingManager {

    val channels = mutableMapOf<String, MessageChannel<*>>()


    abstract fun <T> createChannel(channel: String, clazz: Class<T>): MessageChannel<T>

    override fun <T> getChannel(channel: String, clazz: Class<T>): MessageChannel<T> {
        validateChannelName(channel)

        @Suppress("UNCHECKED_CAST")
        return channels.computeIfAbsent(channel) { createChannel(channel, clazz) } as MessageChannel<T>
    }

    private fun validateChannelName(channel: String) {
        require(channel.isNotEmpty()) { "Channel name is empty" }
        require(channel.length <= 255) { "Channel name is too long" }
    }
}
