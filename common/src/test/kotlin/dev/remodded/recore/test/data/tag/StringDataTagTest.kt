package dev.remodded.recore.test.data.tag

import com.google.gson.JsonPrimitive
import dev.remodded.recore.api.data.tag.StringDataTag
import dev.remodded.recore.api.data.tag.cast
import kotlin.test.Test
import kotlin.test.assertEquals

class StringDataTagTest : DataTagTestBase() {

    @Test
    fun getValue() {
        val dataTag = provider.from("test")

        assertEquals("test", dataTag.getValue())
    }

    @Test
    fun putValue() {
        val dataTag = provider.from("test")

        assertEquals("test", dataTag.putValue("test2"))

        assertEquals("test2", dataTag.putValue("3"))
    }

    @Test
    fun toJson() {
        assertEquals(JsonPrimitive("test"), provider.from("test").toJson())
        assertEquals(JsonPrimitive("test2"), provider.from("test2").toJson())
    }

    @Test
    fun fromJson() {
        assertEquals("test", provider.from(JsonPrimitive("test")).cast<StringDataTag>().getValue())
        assertEquals("test2", provider.from(JsonPrimitive("test2")).cast<StringDataTag>().getValue())
    }

    @Test
    fun stringify() {
        assertEquals("test", provider.from("test").toString())
        assertEquals("test2", provider.from("test2").toString())
    }
}
