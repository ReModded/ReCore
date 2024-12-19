package dev.remodded.recore.api.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.remodded.recore.api.ReCore
import dev.remodded.recore.api.service.getLazyService

object JsonUtils {
    val GSON: Gson by ReCore.INSTANCE.serviceProvider.getLazyService()

    inline fun <reified T> fromJson(json: String): T {
        return fromJson(json, object : TypeToken<T>() {})
    }

    fun <T> fromJson(json: String, clazz: Class<T>): T {
        return GSON.fromJson(json, clazz)
    }

    fun <T> fromJson(json: String, type: TypeToken<T>): T {
        return GSON.fromJson(json, type)
    }

    fun <T> toJson(obj: T): String {
        return GSON.toJson(obj)
    }
}
