package dev.remodded.recore.common.extention

import dev.remodded.recore.api.extention.Extendable
import dev.remodded.recore.api.extention.Extension

@JvmInline
value class CommonExtensionStorage(
    val extensions: MutableMap<Class<out Extension<*>>, Extension<*>> = HashMap()
) {
    fun <T: Extendable, E : Extension<T>> hasExtension(extension: Class<E>) =
        extensions.containsKey(extension)

    @Suppress("UNCHECKED_CAST")
    fun <T: Extendable, E : Extension<T>> getExtension(extension: Class<E>): E? =
        extensions[extension] as? E

    fun <T: Extendable, E : Extension<T>> addExtension(subject: T, extension: Class<E>): E {
        @Suppress("UNCHECKED_CAST")
        val instance: E = extension.declaredConstructors
            .find { it.parameterTypes.size == 1 && it.parameterTypes[0].isInstance(subject) }
            ?.newInstance(subject) as? E ?:
            extension.getDeclaredConstructor().newInstance()

        extensions.put(extension, instance)
        return instance
    }

    fun <T: Extendable, E : Extension<T>> removeExtension(extension: Class<E>): Boolean =
        extensions.remove(extension) != null
}
