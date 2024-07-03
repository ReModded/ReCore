package dev.remodded.recore.velocity.messaging.channel

import dev.remodded.recore.api.messaging.MessageChannel
import dev.remodded.recore.api.messaging.MessageListener
import dev.remodded.recore.common.messaging.channel.CommonChannelMessagePacket
import dev.remodded.recore.api.utils.JsonUtils


class VelocityMessageChannel<T>(private val manager: VelocityChannelMessagingManager, val channel: String, val clazz: Class<T>) : MessageChannel<T> {

    private val listeners: MutableList<MessageListener<T>> = mutableListOf()

    override fun sendMessage(message: T): Boolean {
        val packet = CommonChannelMessagePacket(channel, JsonUtils.toJson(message))

        return manager.broadcastMessage(packet) == manager.serversConnections.size
    }

    override fun registerListener(listener: MessageListener<T>) {
        listeners.add(listener)
    }

    fun handleMessage(message: String) {
        val msg = JsonUtils.fromJson(message, clazz)
        listeners.forEach { it.onMessage(msg) }
    }
}
