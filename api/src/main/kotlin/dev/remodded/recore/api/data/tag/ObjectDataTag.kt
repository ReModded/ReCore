package dev.remodded.recore.api.data.tag

interface ObjectDataTag : DataTag, MutableMap<String, DataTag> {
    fun tryGet(key: String): DataTag?

    fun <T: DataTag> get(key: String, type: Class<T>): T
    fun <T: DataTag> tryGet(key: String, type: Class<T>): T?

    fun <T: DataTag> getOrPut(key: String, type: Class<T>, defaultValue: T): T
    fun <T: DataTag> getOrPut(key: String, type: Class<T>, defaultValue: () -> T): T

    // Value
    fun <T: Any> putValue(key: String, value: T): DataTag
    fun <T: Any> getValue(key: String, type: Class<T>): T
    fun <T: Any> tryGetValue(key: String, type: Class<T>): T?
}

inline fun <reified T: DataTag> ObjectDataTag.get(key: String) = get(key, T::class.javaObjectType)
inline fun <reified T: DataTag> ObjectDataTag.tryGet(key: String) = tryGet(key, T::class.javaObjectType)

inline fun <reified T: DataTag> ObjectDataTag.getOrPut(key: String, defaultValue: T) = getOrPut(key, T::class.javaObjectType, defaultValue)
inline fun <reified T: DataTag> ObjectDataTag.getOrPut(key: String, noinline defaultValue: () -> T) = getOrPut(key, T::class.javaObjectType, defaultValue)

inline fun <reified T: Any> ObjectDataTag.getValue(key: String) = getValue(key, T::class.javaObjectType)
inline fun <reified T: Any> ObjectDataTag.tryGetValue(key: String) = tryGetValue(key, T::class.javaObjectType)
