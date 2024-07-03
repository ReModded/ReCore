package dev.remodded.recore.velocity.messaging.channel.dummy

import io.netty.buffer.ByteBufAllocator
import io.netty.channel.*
import io.netty.util.Attribute
import io.netty.util.AttributeKey
import java.net.SocketAddress


class DummyNettyChannel : Channel {
    override fun id(): ChannelId? {
        return null
    }

    override fun eventLoop(): EventLoop? {
        return null
    }

    override fun parent(): Channel? {
        return null
    }

    override fun config(): ChannelConfig? {
        return null
    }

    override fun isOpen(): Boolean {
        return false
    }
    override fun isRegistered(): Boolean {
        return false
    }

    override fun isActive(): Boolean {
        return true
    }

    override fun metadata(): ChannelMetadata? {
        return null
    }

    override fun localAddress(): SocketAddress? {
        return null
    }

    override fun remoteAddress(): SocketAddress? {
        return null
    }

    override fun closeFuture(): ChannelFuture? {
        return null
    }

    override fun isWritable(): Boolean {
        return false
    }

    override fun bytesBeforeUnwritable(): Long {
        return 0
    }

    override fun bytesBeforeWritable(): Long {
        return 0
    }

    override fun unsafe(): Channel.Unsafe? {
        return null
    }

    override fun pipeline(): ChannelPipeline? {
        return null
    }

    override fun alloc(): ByteBufAllocator? {
        return null
    }

    override fun bind(localAddress: SocketAddress?): ChannelFuture? {
        return null
    }

    override fun bind(localAddress: SocketAddress?, promise: ChannelPromise?): ChannelFuture? {
        return null
    }

    override fun connect(remoteAddress: SocketAddress?): ChannelFuture? {
        return null
    }

    override fun connect(remoteAddress: SocketAddress?, localAddress: SocketAddress?): ChannelFuture? {
        return null
    }

    override fun connect(remoteAddress: SocketAddress?, promise: ChannelPromise?): ChannelFuture? {
        return null
    }

    override fun connect(
        remoteAddress: SocketAddress?,
        localAddress: SocketAddress?,
        promise: ChannelPromise?
    ): ChannelFuture? {
        return null
    }

    override fun disconnect(): ChannelFuture? {
        return null
    }

    override fun disconnect(promise: ChannelPromise?): ChannelFuture? {
        return null
    }

    override fun close(): ChannelFuture? {
        return null
    }

    override fun close(promise: ChannelPromise?): ChannelFuture? {
        return null
    }

    override fun deregister(): ChannelFuture? {
        return null
    }

    override fun deregister(promise: ChannelPromise?): ChannelFuture? {
        return null
    }

    override fun read(): Channel? {
        return null
    }

    override fun write(msg: Any?): ChannelFuture? {
        return null
    }

    override fun write(msg: Any?, promise: ChannelPromise?): ChannelFuture? {
        return null
    }

    override fun flush(): Channel? {
        return null
    }

    override fun writeAndFlush(msg: Any?, promise: ChannelPromise?): ChannelFuture? {
        return null
    }

    override fun writeAndFlush(msg: Any?): ChannelFuture? {
        return null
    }

    override fun newPromise(): ChannelPromise? {
        return null
    }

    override fun newProgressivePromise(): ChannelProgressivePromise? {
        return null
    }


    override fun newSucceededFuture(): ChannelFuture? {
        return null
    }

    override fun newFailedFuture(cause: Throwable?): ChannelFuture? {
        return null
    }

    override fun voidPromise(): ChannelPromise? {
        return null
    }

    override fun <T> attr(key: AttributeKey<T>?): Attribute<T>? {
        return null
    }


    override fun <T> hasAttr(key: AttributeKey<T>?): Boolean {
        return false
    }

    override fun compareTo(other: Channel?): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        return other is DummyNettyChannel && this.compareTo(other as Channel?) == 0
    }

    override fun hashCode(): Int {
        return 0
    }
}
