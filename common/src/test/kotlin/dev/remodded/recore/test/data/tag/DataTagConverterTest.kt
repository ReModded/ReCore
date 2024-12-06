package dev.remodded.recore.test.data.tag

import dev.remodded.recore.api.data.tag.DataTag
import dev.remodded.recore.api.data.tag.value
import kotlin.test.Test
import kotlin.test.assertEquals

class DataTagConverterTest : DataTagTestBase() {
    @Test
    fun from() {
        assertEquals(1, DataTag.from(1).getValue())
        assertEquals(1.0, DataTag.from(1.0).getValue())
        assertEquals("a", DataTag.from("a").getValue())

        DataTag.from(true).apply {
            assertEquals(1, getValue())
            assertEquals(true, getBool())

            assertEquals(Boolean::class.javaObjectType, getType())
        }

        assertEquals(1, DataTag.value(DataTag.from(DataTag.from(1))))
    }
}
