package dev.remodded.recore.common.cache.database

import dev.remodded.recore.api.ReCore
import dev.remodded.recore.api.cache.Cache
import dev.remodded.recore.api.utils.JsonUtils
import dev.remodded.recore.api.utils.getOne
import dev.remodded.recore.api.utils.use
import java.sql.Connection

class DatabaseCache<T>(override val name: String, override val entryType: Class<T>) : Cache<T> {

    init {
        datasource.connection.use {
            createQuery(name).execute()
        }
    }

    companion object {
        private val datasource = ReCore.INSTANCE.databaseProvider.getDataSource()

        private fun Connection.createQuery(name: String) = prepareStatement("CREATE TABLE IF NOT EXISTS ${getCacheName(name)} (key TEXT PRIMARY KEY, value TEXT)")

        private fun Connection.getEntryQuery(name: String, key: String) =
            prepareStatement("SELECT * FROM ${getCacheName(name)} WHERE key = ?").apply {
                setString(1, key)
            }
        private fun Connection.setEntryQuery(name: String, key: String, value: String) =
            prepareStatement("INSERT INTO ${getCacheName(name)} (key, value) VALUES (?, ?) ON CONFLICT(key) DO UPDATE SET value = excluded.value").apply {
                setString(1, key)
                setString(2, value)
            }
        private fun Connection.removeEntryQuery(name: String, key: String) =
            prepareStatement("DELETE FROM ${getCacheName(name)} WHERE key = ?").apply {
                setString(1, key)
            }
        private fun Connection.getEntryCountQuery(name: String) =
            prepareStatement("SELECT COUNT(*) FROM ${getCacheName(name)}")

        private fun getCacheName(name: String) = if (name.startsWith("cache_")) name else "cache_$name"
    }

    override val size: Int
        get() = datasource.connection.use { getEntryCountQuery(name).getOne { getInt(1) } ?: 0 }

    @Suppress("ReplaceSizeZeroCheckWithIsEmpty")
    override fun isEmpty() = size == 0


    override fun containsKey(key: String): Boolean {
        return datasource.connection.use {
            getEntryQuery(name, key).executeQuery().next()
        }
    }

    override fun get(key: String): T? {
        return datasource.connection.use {
            getEntryQuery(name, key).getOne {
                JsonUtils.fromJson(getString("value"), entryType)
            }
        }
    }

    override fun remove(key: String): T? {
        return datasource.connection.use {
            val value = get(key)
            if (value != null)
                removeEntryQuery(name, key).execute()
            value
        }
    }

    override fun putAll(from: Map<out String, T>) {
        from.forEach { (key, value) -> put(key, value) }
    }

    override fun put(key: String, value: T): T? {
        val oldValue = get(key)
        datasource.connection.use {
            setEntryQuery(name, key, JsonUtils.toJson(value)).execute()
        }
        return oldValue
    }

    override val keys: MutableSet<String> get() = TODO("Not yet implemented")
    override val values: MutableCollection<T> get() = TODO("Not yet implemented")
    override val entries: MutableSet<MutableMap.MutableEntry<String, T>> get() = TODO("Not yet implemented")
    override fun clear() = TODO("Not yet implemented")
    override fun containsValue(value: T) = TODO("Not yet implemented")
}
