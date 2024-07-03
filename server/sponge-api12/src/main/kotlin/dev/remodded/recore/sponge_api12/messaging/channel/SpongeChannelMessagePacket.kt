package dev.remodded.recore.sponge_api12.messaging.channel

import dev.remodded.recore.common.messaging.channel.CommonChannelMessagePacket
import org.spongepowered.api.network.channel.ChannelBuf
import org.spongepowered.api.network.channel.packet.Packet


class SpongeChannelMessagePacket(channel: String, message: String) : Packet {
    var packet = CommonChannelMessagePacket(channel, message)

    @Suppress("unused")
    constructor() : this("", "")

    override fun read(buf: ChannelBuf) {
        val channel = buf.readUTF()
        val message = buf.readUTF()
        packet = CommonChannelMessagePacket(channel, message)
    }

    override fun write(buf: ChannelBuf) {
        buf.writeUTF(packet.channel)
        buf.writeUTF(packet.message)
    }
}
