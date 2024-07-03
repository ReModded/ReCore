package dev.remodded.recore.sponge_api12.messaging.channel

import dev.remodded.recore.api.messaging.MessageChannel
import dev.remodded.recore.api.messaging.MessageListener
import dev.remodded.recore.api.utils.JsonUtils

class SpongeChannelMessageChannel<T>(private val manager: SpongeChannelMessagingManager, private val channel: String, private val clazz: Class<T>) : MessageChannel<T> {

    private val listeners = ArrayList<MessageListener<T>>()

    init {
        manager.binding.addHandler { packet, _ ->
            val msg = JsonUtils.fromJson(packet.packet.message, clazz)
            listeners.forEach { listener ->
                listener.onMessage(msg)
            }
        }
    }

    override fun sendMessage(message: T): Boolean {
        val msg = JsonUtils.toJson(message)
        val connection = manager.proxyConnection
        if (connection != null) {
            manager.channel.sendTo(manager.proxyConnection, SpongeChannelMessagePacket(channel, msg))
                .thenAccept {
//                    println("Message send successfully")
                }
                .exceptionally {
                    println("Error sending message")
                    it.printStackTrace()
                    return@exceptionally null
                }

            return true
        }
        return false
    }

    override fun registerListener(listener: MessageListener<T>) {
        listeners.add(listener)
    }
}
