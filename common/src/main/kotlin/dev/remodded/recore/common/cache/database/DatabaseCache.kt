package dev.remodded.recore.common.cache.database

import dev.remodded.recore.api.ReCore
import dev.remodded.recore.api.cache.Cache
import dev.remodded.recore.api.database.use
import dev.remodded.recore.api.utils.JsonUtils

class DatabaseCache<T>(override val name: String, override val entryType: Class<T>) : Cache<T> {

    init {
        datasource.connection.use {
            prepareStatement(createQuery(name)).execute()
        }
    }

    companion object {
        private val datasource = ReCore.INSTANCE.databaseProvider.getDataSource()

        private fun createQuery(name: String) = "CREATE TABLE IF NOT EXISTS ${getCacheName(name)} (key TEXT PRIMARY KEY, value TEXT)"

        private fun getEntryQuery(name: String, key: String) = "SELECT * FROM ${getCacheName(name)} WHERE key = '$key'"
        private fun setEntryQuery(name: String, key: String, value: String) = "INSERT INTO ${getCacheName(name)} (key, value) VALUES ('$key', '$value') ON CONFLICT(key) DO UPDATE SET value = '$value'"
        private fun removeEntryQuery(name: String, key: String) = "DELETE FROM ${getCacheName(name)} WHERE key = '$key'"
        private fun getEntryCountQuery(name: String) = "SELECT COUNT(*) FROM ${getCacheName(name)}"

        private fun getCacheName(name: String) = if (name.startsWith("cache_")) name else "cache_$name"
    }

    override val size: Int
        get() = datasource.connection.use { prepareStatement(getEntryCountQuery(name)).executeQuery().getInt(1) }

    override fun isEmpty() = size == 0


    override fun containsKey(key: String): Boolean {
        return datasource.connection.use {
            prepareStatement(getEntryQuery(name, key)).executeQuery().next()
        }
    }

    override fun get(key: String): T? {
        return datasource.connection.use {
            prepareStatement(getEntryQuery(name, key)).executeQuery().use {
                if (next()) {
                    JsonUtils.fromJson(getString("value"), entryType)
                } else {
                    null
                }
            }
        }
    }

    override fun remove(key: String): T? {
        return datasource.connection.use {
            val value = get(key)
            if (value != null)
                prepareStatement(removeEntryQuery(name, key)).execute()
            value
        }
    }

    override fun putAll(from: Map<out String, T>) {
        from.forEach { (key, value) -> put(key, value) }
    }

    override fun put(key: String, value: T): T? {
        val oldValue = get(key)
        datasource.connection.use {
            prepareStatement(setEntryQuery(name, key, JsonUtils.toJson(value))).execute()
        }
        return oldValue
    }

    override val keys: MutableSet<String> get() = throw UnsupportedOperationException()
    override val values: MutableCollection<T> get() = throw UnsupportedOperationException()
    override val entries: MutableSet<MutableMap.MutableEntry<String, T>> get() = throw UnsupportedOperationException()
    override fun clear()  = throw UnsupportedOperationException()
    override fun containsValue(value: T) = throw UnsupportedOperationException()
}
