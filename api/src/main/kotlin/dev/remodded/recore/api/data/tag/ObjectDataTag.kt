package dev.remodded.recore.api.data.tag

interface ObjectDataTag : DataTag, MutableMap<String, DataTag> {
    fun <T: DataTag> get(key: String, type: Class<T>): T
    fun <T: DataTag> tryGet(key: String, type: Class<T>): T?

    fun <T: DataTag> getOrPut(key: String, type: Class<T>, defaultValue: T): T
    fun <T: DataTag> getOrPut(key: String, type: Class<T>, defaultValue: () -> T): T
}

inline fun <reified T: DataTag> ObjectDataTag.get(key: String) = get(key, T::class.java)
inline fun <reified T: DataTag> ObjectDataTag.tryGet(key: String) = tryGet(key, T::class.java)

inline fun <reified T: DataTag> ObjectDataTag.getOrPut(key: String, defaultValue: T) = getOrPut(key, T::class.java, defaultValue)
inline fun <reified T: DataTag> ObjectDataTag.getOrPut(key: String, noinline defaultValue: () -> T) = getOrPut(key, T::class.java, defaultValue)
