package dev.remodded.recore.paper.messaging.channel

import com.google.common.io.ByteStreams
import dev.remodded.recore.common.messaging.channel.CommonChannelMessagePacket

object PaperMessagePacket {
    fun write(packet: CommonChannelMessagePacket): ByteArray {
        val out = ByteStreams.newDataOutput()
        out.writeByte(0) // Fix for sponge packets which treats a first byte as some sort of opcode
        out.writeUTF(packet.channel)
        out.writeUTF(packet.message)

        return out.toByteArray()
    }

    fun read(data: ByteArray): CommonChannelMessagePacket {
        val input = ByteStreams.newDataInput(data)

        if (data[1] == data[0] && data[0] == 0.toByte())
            input.skipBytes(1) // Fix for sponge packets which treats a first byte as some sort of opcode

        val channel = input.readUTF()
        val message = input.readUTF()
        return CommonChannelMessagePacket(channel, message)
    }
}
