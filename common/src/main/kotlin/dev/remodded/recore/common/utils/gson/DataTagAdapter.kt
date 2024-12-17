package dev.remodded.recore.common.utils.gson

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import dev.remodded.recore.api.data.tag.*
import dev.remodded.recore.common.data.tag.NumberDataTag
import dev.remodded.recore.common.data.tag.NumberDataTag.NumericType

object DataTagAdapter : TypeAdapter<DataTag>() {
    override fun write(out: JsonWriter, value: DataTag?) {
        when (value) {
            null -> out.nullValue()
            is StringDataTag -> out.value(value.getValue())
            is NumericDataTag ->
                if (value.getType() == Boolean::class.javaObjectType)
                    out.value(value.getBool())
                else {
                    // Number wrapping for type safety
                    out.beginObject()
                    @Suppress("UNCHECKED_CAST")
                    out.name("@type").value((NumericType.from(value.getType() as Class<Number>) ?: throw IllegalArgumentException("Invalid number!")).ordinal)
                    out.name("value").value(value.getValue())
                    out.endObject()
                }
            is ObjectDataTag -> {
                out.beginObject()
                for (entry in value.entries)
                    write(out.name(entry.key), entry.value)
                out.endObject()
            }
            is ListDataTag<*> -> {
                if (value.isEmpty()) {
                    out.beginArray()
                    out.endArray()
                    return
                }

                val first = value.first()

                out.beginArray()

                if (
                    first is NumericDataTag &&
                    value.all { it.getType() == first.getType() }
                ) {
                    @Suppress("UNCHECKED_CAST")
                    val type = NumericType.from(first.getType() as Class<Number>)
                        ?: throw IllegalArgumentException("Unknown data type ${value.javaClass.name}")

                    out.value("@type=${type}")
                    for (element in value)
                        out.value((element as NumericDataTag).getValue())
                }
                else
                    for (element in value)
                        write(out, element)

                out.endArray()
            }
            else -> throw IllegalArgumentException("Unsupported value type: ${value::class.java}")
        }
    }

    override fun read(`in`: JsonReader): DataTag? {
        return when (`in`.peek()) {
            JsonToken.NULL -> {
                `in`.nextNull()
                null
            }
            JsonToken.STRING -> DataTag.from(`in`.nextString())
            JsonToken.BOOLEAN -> DataTag.from(`in`.nextBoolean())
            JsonToken.NUMBER -> {
                val value = `in`.nextString()
                val number: Number = try {
                    value.toInt()
                } catch (_: NumberFormatException) {
                    try {
                        value.toLong()
                    } catch (_: NumberFormatException) {
                        value.toDouble()
                    }
                }
                DataTag.from(number)
            }
            JsonToken.BEGIN_ARRAY -> {
                `in`.beginArray()
                val list = DataTag.listTag<DataTag>()

                var firstString = true
                var type: NumericType? = null

                while (`in`.hasNext())
                    if (firstString && `in`.peek() == JsonToken.STRING) {
                        firstString = false
                        val str = `in`.nextString()
                        if (str.startsWith("@type=")) {
                            type = NumericType.entries.getOrNull(str.substring("@type=".length).toInt())
                            if (type == null)
                                throw IllegalArgumentException("Unknown data type $str")

                            if (list.isNotEmpty()) {
                                @Suppress("UNCHECKED_CAST")
                                val tmp = list.toList() as List<NumericDataTag>
                                list.clear()
                                for (num in tmp)
                                    list.add(NumberDataTag(type.cast(num.getValue())))
                            }
                        }
                        else
                            list.add(DataTag.from(str))
                    }
                    else if (type != null) {
                        if (`in`.peek() != JsonToken.NUMBER)
                            throw IllegalArgumentException("Malformed ListDataTag<NumericDataTag> Json representation, expected Number, got ${`in`.peek()}")

                        val num: Number = when (type) {
                            NumericType.Byte,
                            NumericType.Short,
                            NumericType.Int -> `in`.nextInt()
                            NumericType.Long -> `in`.nextLong()
                            NumericType.Float,
                            NumericType.Double -> `in`.nextDouble()
                            else -> throw IllegalArgumentException("Unsupported data type $type")
                        }
                        list.add(NumberDataTag(type.cast(num)))
                    }
                    else
                        read(`in`)?.let { list.add(it) }

                `in`.endArray()
                list
            }
            JsonToken.BEGIN_OBJECT -> {
                `in`.beginObject()
                val tag = DataTag.objectTag()

                while (`in`.hasNext()) {
                    val key = `in`.nextName()
                    read(`in`)?.let { tag[key] = it }
                }

                `in`.endObject()

                if (tag.containsKey("@type")) {
                    val type = NumericType.entries.getOrNull(tag.getValue<Int>("@type"))
                        ?: throw IllegalArgumentException("Invalid number!")
                    NumberDataTag(type.cast(tag.getValue<Number>("value")))
                }
                else
                    tag
            }
            else -> throw IllegalArgumentException("Unsupported value type: ${`in`.peek()}")
        }
    }
}
