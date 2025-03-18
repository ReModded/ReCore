package dev.remodded.recore.common.utils

import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0

fun interface Mapper<T, R> {
    fun map(value: T): R
}

interface BiMapper<T, R> : Mapper<T, R> {
    fun unmap(value: R): T
}

interface MappedDelegate<T, R> {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): R
}
interface MutableMappedDelegate<T, R> : MappedDelegate<T, R> {
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: R)
}

fun <T, R> KProperty0<T>.mapped(mapper: Mapper<T, R>): MappedDelegate<T, R> =
    MappedDelegateImpl(this, mapper)

fun <T, R> KMutableProperty0<T>.mapped(mapper: BiMapper<T, R>): MutableMappedDelegate<T, R> =
    MutableMappedDelegateImpl(this, mapper)

fun <T, R> KMutableProperty0<T>.mapped(getMapper: Mapper<T, R>, setMapper: Mapper<R, T>): MutableMappedDelegate<T, R> =
    MutableMappedDelegateImpl(this, object : BiMapper<T, R> {
        override fun map(value: T): R = getMapper.map(value)
        override fun unmap(value: R): T = setMapper.map(value)
    })

private class MutableMappedDelegateImpl<T, R>(
    override val delegate: KMutableProperty0<T>,
    override val mapper: BiMapper<T, R>
) : MappedDelegateImpl<T, R>(delegate, mapper), MutableMappedDelegate<T, R> {
    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: R) {
        delegate.set(mapper.unmap(value))
    }
}

private open class MappedDelegateImpl<T, R>(
    open val delegate: KProperty0<T>,
    open val mapper: Mapper<T, R>
): MappedDelegate<T, R> {
    override operator fun getValue(thisRef: Any?, property: KProperty<*>): R {
        return mapper.map(delegate.get())
    }
}
