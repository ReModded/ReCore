package dev.remodded.recore.api.extention

interface ExtensionProvider {
    fun <T: Extendable, E: Extension<T>> hasExtension(subject: T, extension: Class<E>): Boolean
    fun <T: Extendable, E: Extension<T>> getExtension(subject: T, extension: Class<E>): E?

    fun <T: Extendable, E: Extension<T>> addExtension(subject: T, extension: Class<E>): E
    fun <T: Extendable, E: Extension<T>> removeExtension(subject: T, extension: Class<E>): Boolean
}
