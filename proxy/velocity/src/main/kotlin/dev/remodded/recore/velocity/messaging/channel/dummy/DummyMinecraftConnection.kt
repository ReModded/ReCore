package dev.remodded.recore.velocity.messaging.channel.dummy

import com.velocitypowered.proxy.VelocityServer
import com.velocitypowered.proxy.connection.MinecraftConnection
import dev.remodded.recore.velocity.ReCoreVelocity
import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext

class DummyMinecraftConnection(channel: Channel) : MinecraftConnection(channel, ReCoreVelocity.INSTANCE.proxy as VelocityServer) {

    override fun channelActive(ctx: ChannelHandlerContext?) {
        if (activeSessionHandler != null) {
            activeSessionHandler!!.connected()
        }
    }

    override fun channelInactive(ctx: ChannelHandlerContext?) {
        if (activeSessionHandler != null) {
            activeSessionHandler!!.disconnected()
        }
    }
}
