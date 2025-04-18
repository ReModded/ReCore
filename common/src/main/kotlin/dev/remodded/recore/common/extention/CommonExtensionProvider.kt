package dev.remodded.recore.common.extention

import dev.remodded.recore.api.extention.Extendable
import dev.remodded.recore.api.extention.Extension
import dev.remodded.recore.api.extention.ExtensionProvider
import java.util.*

class CommonExtensionProvider : ExtensionProvider {
    // TODO: move to Extendable
    val storages = HashMap<UUID, CommonExtensionStorage>()

    override fun <T : Extendable, E : Extension<T>> hasExtension(subject: T, extension: Class<E>): Boolean {
        return getStorage(subject).hasExtension(extension)
    }

    override fun <T : Extendable, E : Extension<T>> getExtension(subject: T, extension: Class<E>): E? {
        return getStorage(subject).getExtension(extension)
    }

    override fun <T : Extendable, E : Extension<T>> addExtension(subject: T, extension: Class<E>): E {
        return getStorage(subject).addExtension(subject, extension)
    }

    override fun <T : Extendable, E : Extension<T>> removeExtension(subject: T, extension: Class<E>): Boolean {
        return getStorage(subject).removeExtension(extension)
    }

    fun getStorage(extendable: Extendable): CommonExtensionStorage {
        if (extendable is ExtensionHolder)
            return storages.getOrPut(extendable.id) { CommonExtensionStorage() }

        throw IllegalArgumentException("Unsupported non Extendable type: ${extendable::class.simpleName}")
    }
}
