package dev.remodded.recore.common.utils.gson

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dev.remodded.recore.api.data.tag.DataTag

object GsonProvider {
    val GSON: Gson = GsonBuilder()
        .registerTypeAdapter(DataTag::class.java, DataTagAdapter)
        .create()
}
