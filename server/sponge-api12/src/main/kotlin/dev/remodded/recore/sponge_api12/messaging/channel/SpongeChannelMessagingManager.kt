package dev.remodded.recore.sponge_api12.messaging.channel

import dev.remodded.recore.api.messaging.MessageChannel
import dev.remodded.recore.common.Constants
import dev.remodded.recore.common.messaging.channel.CommonChannelMessagingManager
import dev.remodded.recore.sponge_api12.ReCoreSponge
import org.spongepowered.api.ResourceKey
import org.spongepowered.api.Sponge
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.network.ServerSideConnectionEvent
import org.spongepowered.api.network.EngineConnection
import org.spongepowered.api.network.channel.packet.PacketChannel
import org.spongepowered.common.network.channel.ConnectionUtil

class SpongeChannelMessagingManager : CommonChannelMessagingManager() {

    val channel = Sponge.game().channelManager().ofType(ResourceKey.of(Constants.ID, CHANNEL_NAME), PacketChannel::class.java)!!
    val binding = channel.register(SpongeChannelMessagePacket::class.java, 0)!!
    var proxyConnection: EngineConnection? = null

    init {
        Sponge.eventManager().registerListeners(ReCoreSponge.INSTANCE.plugin, this)
    }

    override fun <T> createChannel(channel: String, clazz: Class<T>): MessageChannel<T> {
        return SpongeChannelMessageChannel(this, channel, clazz)
    }

    @Listener
    fun onConnection(ev: ServerSideConnectionEvent.Auth) {
        if (ev.profile().name().get() == FAKE_PLAYER_NAME) {
//            println("Proxy connection established")
            ConnectionUtil.getRegisteredChannels(ev.connection()).add(channel.key())
            proxyConnection = ev.connection()
        }
    }
}



