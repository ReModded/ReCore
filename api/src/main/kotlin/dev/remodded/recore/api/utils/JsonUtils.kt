package dev.remodded.recore.api.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object JsonUtils {
    private val gson = Gson()

    inline fun <reified T> fromJson(json: String): T {
        return fromJson(json, object : TypeToken<T>() {})
    }

    fun <T> fromJson(json: String, clazz: Class<T>): T {
        return gson.fromJson(json, clazz)
    }

    fun <T> fromJson(json: String, type: TypeToken<T>): T {
        return gson.fromJson(json, type)
    }

    fun <T> toJson(obj: T): String {
        return gson.toJson(obj)
    }
}
