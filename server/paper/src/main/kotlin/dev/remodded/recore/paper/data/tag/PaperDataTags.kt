package dev.remodded.recore.paper.data.tag

import dev.remodded.recore.api.ReCore
import dev.remodded.recore.api.data.tag.*
import dev.remodded.recore.api.service.getLazyService
import net.minecraft.nbt.*

private val provider: DataTagProvider by ReCore.INSTANCE.serviceProvider.getLazyService()

// Tag -> DataTag

fun StringTag.toDataTag() = provider.from(this.asString)
fun NumericTag.toDataTag() = provider.from(this.asNumber)

fun CollectionTag<*>.toDataTag(): ListDataTag<DataTag> = provider.listTag<DataTag>(this.size).also { tag -> forEach { tag.add(it.toDataTag()) }}

fun CompoundTag.toDataTag() = provider.objectTag().apply {
    for (key in this@toDataTag.allKeys)
        this.put(key, this@toDataTag.get(key)!!.toDataTag())
}

fun Tag.toDataTag(): DataTag = when(this) {
    is StringTag -> this.toDataTag()
    is NumericTag -> this.toDataTag()
    is CollectionTag<*> -> this.toDataTag() as DataTag
    is CompoundTag -> this.toDataTag()
    else -> throw IllegalArgumentException("Unsupported NBT Tag type: ${this::class.simpleName}")
}

// DataTag -> Tag

fun StringDataTag.toTag(): StringTag = StringTag.valueOf(this.getValue())
fun NumericDataTag.toTag(): NumericTag = when(this.getValue()) {
    is Byte -> ByteTag.valueOf(this.getValue<Byte>())
    is Short -> ShortTag.valueOf(this.getValue<Short>())
    is Int -> IntTag.valueOf(this.getValue<Int>())
    is Long -> LongTag.valueOf(this.getValue<Long>())
    is Float -> FloatTag.valueOf(this.getValue<Float>())
    is Double -> DoubleTag.valueOf(this.getValue<Double>())
    else -> throw IllegalArgumentException("Unsupported DataTag type: ${this.getValue().javaClass.simpleName}")
}

fun ListDataTag<*>.toTag(): CollectionTag<*> =
    if (!this.isEmpty() &&
        this.first() is NumericDataTag &&
        this.all { it.getType() == this.first().getType() }
    ) {
        when (this.first().cast<NumericDataTag>().getValue()) {
            is Byte -> ByteArrayTag(this.map { it.cast<NumericDataTag>().getValue<Byte>() })
            is Int -> IntArrayTag(this.map { it.cast<NumericDataTag>().getValue<Int>() })
            is Long -> LongArrayTag(this.map { it.cast<NumericDataTag>().getValue<Long>() })
            else -> ListTag().apply { addAll(this@toTag.map { it.toTag() }) }
        }
    }
    else {
        ListTag().apply {
            addAll(this@toTag.map { it.toTag() })
        }
    }

fun ObjectDataTag.toTag(): CompoundTag = CompoundTag().apply {
    this@toTag.entries.forEach { (key, value) -> this.put(key, value.toTag()) }
}

fun DataTag.toTag(): Tag = when(this) {
    is StringDataTag -> this.toTag()
    is NumericDataTag -> this.toTag()
    is ListDataTag<*> -> this.toTag()
    is ObjectDataTag -> this.toTag()
    else -> throw IllegalArgumentException("Unsupported DataTag type: ${this::class.simpleName}")
}
