package dev.remodded.recore.test.data.tag

import dev.remodded.recore.api.data.tag.NumericDataTag
import dev.remodded.recore.api.data.tag.StringDataTag
import dev.remodded.recore.api.data.tag.ofType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ListDataTagTest : DataTagTestBase() {

    @Test
    fun construction() {
        assertEquals(listOf(), provider.from(listOf()))
    }

    @Test
    fun ofType() {
        val list = provider.listTag(listOf("a", "b", "c"))
        assertEquals("a", list.ofType<StringDataTag>()[0].getValue())

        assertFailsWith<ClassCastException> {
            list.ofType<NumericDataTag>()
        }
    }

}
