package dev.remodded.recore.test.data.tag

import dev.remodded.recore.api.data.tag.NumericDataTag
import dev.remodded.recore.api.data.tag.StringDataTag
import dev.remodded.recore.api.data.tag.get
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ObjectDataTagTest : DataTagTestBase() {

    val mapData = mapOf(
        "a" to provider.from("a"),
        "c" to provider.from(1),
    )

    @Test
    fun construction() {
        assertEquals(0, provider.objectTag().size)

        assertEquals(mapData, provider.from(mapData))
    }

    @Test
    fun getCasting() {
        val obj = provider.from(mapData)

        assertEquals("a", obj.get<StringDataTag>("a").getValue())

        assertFailsWith<ClassCastException> {
            obj.get<NumericDataTag>("a")
        }
        assertFailsWith<IllegalArgumentException> {
            obj.get<StringDataTag>("b")
        }

        assertEquals(1, obj.get<NumericDataTag>("c").getValue())
    }

}
