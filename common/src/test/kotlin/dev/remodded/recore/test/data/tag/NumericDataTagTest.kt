package dev.remodded.recore.test.data.tag

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import dev.remodded.recore.api.data.tag.NumericDataTag
import dev.remodded.recore.api.data.tag.cast
import dev.remodded.recore.api.data.tag.getValue
import dev.remodded.recore.test.TestConstants
import kotlin.test.Test
import kotlin.test.assertEquals

class NumericDataTagTest : DataTagTestBase() {

    @Test
    fun getValue() {
        val dataTag = provider.from(2)

        assertEquals(true, dataTag.getBool())

        assertEquals(2, dataTag.getValue<Byte>())
        assertEquals(2, dataTag.getValue<Short>())
        assertEquals(2, dataTag.getValue<Int>())
        assertEquals(dataTag.getValue<Long>(), 2)

        assertEquals(2f, dataTag.getValue<Float>())
        assertEquals(2.0, dataTag.getValue<Double>())

        assertEquals(2, dataTag.getValue<Number>())
    }

    @Test
    fun putValue() {
        val dataTag = provider.from(2.0)

        assertEquals(2, dataTag.putValue(3))

        assertEquals(true, dataTag.putValue(false))

        assertEquals(0, dataTag.putValue<Byte>(12))
        assertEquals(12, dataTag.putValue<Short>(543))
        assertEquals(543, dataTag.putValue<Int>(643))
        assertEquals(643, dataTag.putValue<Long>(7545))

        assertEquals(7545f, dataTag.putValue<Float>(.3f), TestConstants.EPSILON.toFloat())
        assertEquals(0.3, dataTag.putValue<Double>(31.3), TestConstants.EPSILON)

        assertEquals(31, dataTag.putValue<Number>(85))
    }

    @Test
    fun toJson() {
        assertEquals(create(2.toByte()), provider.from(2.toByte()).toJson())
        assertEquals(create(2.toShort()), provider.from(2.toShort()).toJson())
        assertEquals(create(2), provider.from(2).toJson())
        assertEquals(create(2L), provider.from(2L).toJson())

        assertEquals(create(2.0f), provider.from(2.0f).toJson())
        assertEquals(create(2.0), provider.from(2.0).toJson())
    }

    @Test
    fun fromJson() {
        assertEquals(2, provider.from(create(2)).cast<NumericDataTag>().getValue())
        assertEquals(2, provider.from(JsonPrimitive(2)).cast<NumericDataTag>().getValue())

        assertEquals(true, provider.from(JsonPrimitive(true)).cast<NumericDataTag>().getBool())
        assertEquals(false, provider.from(JsonPrimitive(false)).cast<NumericDataTag>().getBool())
    }

    @Test
    fun stringify() {
        assertEquals("2", provider.from(2.toByte()).toString())
        assertEquals("2", provider.from(2.toShort()).toString())
        assertEquals("2", provider.from(2).toString())
        assertEquals("2", provider.from(2L).toString())

        assertEquals("2.0", provider.from(2.0f).toString())
        assertEquals("2.0", provider.from(2.0).toString())
    }


    private fun <T: Number> create(value: T) = JsonObject().apply {
        add("@type", JsonPrimitive(when(value.javaClass) {
            java.lang.Byte::class.java -> 0
            java.lang.Short::class.java -> 1
            java.lang.Integer::class.java -> 2
            java.lang.Long::class.java -> 3
            java.lang.Float::class.java -> 4
            java.lang.Double::class.java -> 5
            else -> throw IllegalArgumentException("Invalid number!")
        }))
        add("value", JsonPrimitive(value))
    }
}
