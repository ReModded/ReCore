package dev.remodded.recore.api.extention

import dev.remodded.recore.api.ReCore
import dev.remodded.recore.api.service.getLazyService

interface Extendable {
    companion object {
        private val provider: ExtensionProvider by ReCore.INSTANCE.serviceProvider.getLazyService()

        @JvmStatic
        fun <T: Extendable, E: Extension<T>> T.hasExtension(extension: Class<E>) = provider.hasExtension(this, extension)
        @JvmStatic
        fun <T: Extendable, E: Extension<T>> T.getExtension(extension: Class<E>) = provider.getExtension(this, extension)

        @JvmStatic
        fun <T: Extendable, E: Extension<T>> T.addExtension(extension: Class<E>) = provider.addExtension(this, extension)
        @JvmStatic
        fun <T: Extendable, E: Extension<T>> T.removeExtension(extension: Class<E>) = provider.removeExtension(this, extension)
    }
}
