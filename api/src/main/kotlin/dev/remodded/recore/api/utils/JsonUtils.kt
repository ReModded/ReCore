package dev.remodded.recore.api.utils

import com.google.gson.Gson

object JsonUtils {
    private val gson = Gson()

    inline fun <reified T> fromJson(json: String): T {
        return fromJson(json, T::class.java)
    }

    fun <T> fromJson(json: String, clazz: Class<T>): T {
        return gson.fromJson(json, clazz)
    }

    fun <T> toJson(obj: T): String {
        return gson.toJson(obj)
    }
}
