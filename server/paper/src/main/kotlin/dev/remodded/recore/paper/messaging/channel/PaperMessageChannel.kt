package dev.remodded.recore.paper.messaging.channel

import dev.remodded.recore.api.messaging.MessageChannel
import dev.remodded.recore.api.messaging.MessageListener
import dev.remodded.recore.api.utils.JsonUtils
import dev.remodded.recore.common.messaging.channel.CommonChannelMessagePacket
import org.slf4j.LoggerFactory

class PaperMessageChannel<T>(private val channel: String, private val clazz: Class<T>) : MessageChannel<T> {

    companion object {
        private val logger = LoggerFactory.getLogger(PaperMessageChannel::class.java)
    }

    private val listeners = ArrayList<MessageListener<T>>()

    fun handlePacket(packet: CommonChannelMessagePacket) {
        val msg = JsonUtils.fromJson(packet.message, clazz)
        listeners.forEach { listener ->
            listener.onMessage(msg)
        }
    }

    override fun sendMessage(message: T): Boolean {
        logger.warn("PaperMessageChannel.sendMessage is not implemented yet!")
        logger.warn("  Awaiting https://github.com/PaperMC/Paper/pull/9826  ")
        logger.warn("=======================================================")
        return false
//        val connection = PaperChannelMessagingManager.INSTANCE.proxyConnection ?: return false
//
//        val msg = JsonUtils.toJson(message)
//        val packet = CommonChannelMessagePacket(channel, msg)
//        val packetData = PaperMessagePacket.write(packet)
//
//        try {
//            connection.sendPluginMessage(ReCorePaperPlatform.INSTANCE, CommonChannelMessagingManager.CHANNEL_ID, packetData)
//            connection.send(ClientboundCustomPayloadPacket(DiscardedPayload(ResourceLocation.parse(CHANNEL_ID), Unpooled.wrappedBuffer(packetData))))
//        } catch (e: Exception) {
//            e.printStackTrace()
//            return false
//        }
//
//        return true
    }

    override fun registerListener(listener: MessageListener<T>) {
        listeners.add(listener)
    }
}
