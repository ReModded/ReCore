package dev.remodded.recore.api.utils

import java.sql.PreparedStatement
import java.sql.ResultSet
import kotlin.use

inline fun <reified T> ResultSet.getObject(key: String): T = getObject(key, T::class.java)
inline fun <reified T> ResultSet.getObject(index: Int): T = getObject(index, T::class.java)

inline fun <T: AutoCloseable, R> T.use(block: T.() -> R) = use { it.block() }

inline fun <R> PreparedStatement.getOne(block: ResultSet.() -> R) = executeQuery().use {
    if (it.next())
        return@use it.block()
    return@use null
}

inline fun PreparedStatement.getAll(block: ResultSet.() -> Unit) = executeQuery().use {
    while (it.next())
        it.block()
}

inline fun <T> PreparedStatement.getAll(converter: ResultSet.() -> T, block: (T) -> Unit) = getAll {
    block(converter())
}
