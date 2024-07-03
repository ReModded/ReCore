package dev.remodded.recore.velocity.messaging.channel.dummy

import com.velocitypowered.proxy.connection.MinecraftSessionHandler
import com.velocitypowered.proxy.connection.backend.VelocityServerConnection
import com.velocitypowered.proxy.connection.util.ConnectionRequestResults
import com.velocitypowered.proxy.protocol.packet.DisconnectPacket
import com.velocitypowered.proxy.protocol.packet.KeepAlivePacket
import com.velocitypowered.proxy.protocol.packet.PluginMessagePacket
import com.velocitypowered.proxy.protocol.packet.config.FinishedUpdatePacket
import dev.remodded.recore.velocity.messaging.channel.VelocityChannelMessagingManager
import java.io.IOException
import java.util.concurrent.CompletableFuture

class DummyConfigSessionHandler(
    private var serverConn: VelocityServerConnection,
    private var resultFuture: CompletableFuture<ConnectionRequestResults.Impl>
) : MinecraftSessionHandler {

    override fun beforeHandle(): Boolean {
        if (!serverConn.isActive) {
            // Obsolete connection
            serverConn.disconnect()
            return true
        }
        return false
    }

    override fun handle(packet: PluginMessagePacket): Boolean {
        VelocityChannelMessagingManager.INSTANCE.handleMessage(packet)
        return true
    }

    override fun handle(packet: KeepAlivePacket): Boolean {
        serverConn.ensureConnected().write(packet)
        return true
    }

    override fun handle(packet: FinishedUpdatePacket): Boolean {
        throw IllegalStateException("Unexpected FinishedUpdatePacket in configuration state")
    }

    override fun handle(packet: DisconnectPacket): Boolean {
        serverConn.disconnect()
        resultFuture.complete(ConnectionRequestResults.forDisconnect(packet, serverConn.server))
        return true
    }

    override fun disconnected() {
        resultFuture.completeExceptionally(
            IOException("Unexpectedly disconnected from remote server")
        )
    }

    override fun exception(throwable: Throwable) {
        serverConn.ensureConnected().association = null
        resultFuture.completeExceptionally(throwable)
    }
}
