package dev.remodded.recore.test.data.tag

import com.google.gson.JsonPrimitive
import dev.remodded.recore.api.data.tag.NumericDataTag
import dev.remodded.recore.api.data.tag.cast
import dev.remodded.recore.api.data.tag.getValue
import kotlin.test.Test
import kotlin.test.assertEquals

class BooleanDataTagTest : DataTagTestBase() {

    @Test
    fun construction() {
        assertEquals(true, provider.from(true).getBool())
        assertEquals(false, provider.from(false).getBool())
    }

    @Test
    fun casting() {
        val dataTag = provider.from(true)

        assertEquals(true, dataTag.getBool())

        assertEquals(1, dataTag.getValue<Byte>())
        assertEquals(1, dataTag.getValue<Short>())
        assertEquals(1, dataTag.getValue<Int>())
        assertEquals(1, dataTag.getValue<Long>())

        assertEquals(1f, dataTag.getValue<Float>())
        assertEquals(1.0, dataTag.getValue<Double>())

        assertEquals(1, dataTag.getValue<Number>())

    }

    @Test
    fun putValue() {
        val dataTag = provider.from(true)

        assertEquals(true, dataTag.putValue(false))

        assertEquals(false, dataTag.putValue(true))

        assertEquals(1, dataTag.putValue<Byte>(0))
        assertEquals(0, dataTag.putValue<Short>(1))
        assertEquals(1, dataTag.putValue<Int>(0))
        assertEquals(0, dataTag.putValue<Long>(1))

        assertEquals(1f, dataTag.putValue<Float>(0.0f))
        assertEquals(0.0, dataTag.putValue<Double>(1.0))

        assertEquals(1, dataTag.putValue<Number>(0))
    }

    @Test
    fun toJson() {
        assertEquals(JsonPrimitive(true), provider.from(true).toJson())
        assertEquals(JsonPrimitive(false), provider.from(false).toJson())
    }

    @Test
    fun fromJson() {
        assertEquals(true, provider.from(JsonPrimitive(true)).cast<NumericDataTag>().getBool())
        assertEquals(false, provider.from(JsonPrimitive(false)).cast<NumericDataTag>().getBool())
    }

    @Test
    fun stringify() {
        assertEquals("true", provider.from(true).toString())
        assertEquals("false", provider.from(false).toString())
    }
}
