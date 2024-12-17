package dev.remodded.recore.test.data.tag

import dev.remodded.recore.api.data.tag.*
import dev.remodded.recore.common.data.tag.NumberDataTag
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GsonDataTagTest : DataTagTestBase() {
    @Test
    fun fromJson() {
        val dataTag = DataTag.fromJson<ObjectDataTag>("""{"test":["@type=${NumberDataTag.NumericType.Float.ordinal}", 1, 0.1], "num": 3, "int":{"@type"=${NumberDataTag.NumericType.Int.ordinal}, "value"=5555.3}}""")
        assertTrue(dataTag.containsKey("test"), "Contains test entry")

        assertNotNull(dataTag.tryGet<ListDataTag<*>>("test"), "Test entry is a ListDataTag")
        val testDataTag = dataTag.get<ListDataTag<*>>("test")
        assertTrue(testDataTag.isNotEmpty(), "Test entry is not empty")
        assertTrue(testDataTag.all { it.getType() == Float::class.javaObjectType }, "Test entry contains only Float")

        assertNotNull(dataTag.tryGet<NumericDataTag>("num"), "Num entry is a NumericDataTag")
        val numDataTag = dataTag.get<NumericDataTag>("num")
        assertTrue(numDataTag.getValue() == 3, "num entry is 3")
        assertTrue(numDataTag.getType() == Int::class.javaObjectType, "num entry is a Float")

        assertNotNull(dataTag.tryGet<NumericDataTag>("int"), "Contains int entry")
        val intDataTag = dataTag.get<NumericDataTag>("int")
        assertTrue(intDataTag.getValue() == 5555, "int entry is 5555")
    }
}
