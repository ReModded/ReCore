package dev.remodded.recore.common.connections.redis

import com.google.gson.Gson
import dev.remodded.recore.api.utils.JsonUtils
import dev.remodded.recore.api.utils.use
import io.netty.buffer.ByteBufAllocator
import io.netty.buffer.ByteBufInputStream
import io.netty.buffer.ByteBufOutputStream
import org.redisson.client.codec.BaseCodec
import org.redisson.client.protocol.Decoder
import org.redisson.client.protocol.Encoder
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap

class GsonCodec(
    val gson: Gson = JsonUtils.GSON,
) : BaseCodec() {

    private val classMap: MutableMap<String, Class<*>> = ConcurrentHashMap<String, Class<*>>()

    private val encoder: Encoder = Encoder { `in` ->
        val out = ByteBufAllocator.DEFAULT.buffer()
        try {
            val os = ByteBufOutputStream(out)
            os.writeUTF(gson.toJson(`in`))
            os.writeUTF(`in`.javaClass.getName())
            os.buffer()
        } catch (e: IOException) {
            out.release()
            throw e
        } catch (e: Exception) {
            out.release()
            throw IOException(e)
        }
    }

    private val decoder = Decoder<Any> { buf, state ->
        ByteBufInputStream(buf).use {
            val string = readUTF()
            val type = readUTF()
            gson.fromJson<Any>(string, getClassFromType(type))
        }
    }

    fun getClassFromType(name: String): Class<*> {
        var clazz = classMap[name]
        if (clazz == null) {
            try {
                clazz = Class.forName(name)
                classMap.put(name, clazz)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }
        }
        if (clazz != null) return clazz
        throw RuntimeException("Could not find a class named: $name")
    }

    override fun getValueDecoder(): Decoder<Any> {
        return decoder
    }

    override fun getValueEncoder(): Encoder {
        return encoder
    }

    @Override
    override fun getClassLoader(): ClassLoader {
        if (gson.javaClass.getClassLoader() != null) {
            return gson.javaClass.getClassLoader()
        }
        return super.getClassLoader()
    }

}
