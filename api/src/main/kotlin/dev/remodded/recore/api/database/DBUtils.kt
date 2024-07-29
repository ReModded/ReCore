@file:JvmName("DBUtils")

package dev.remodded.recore.api.database

import kotlin.use

inline fun <T: AutoCloseable, R> T.use(block: T.() -> R): R {
    return use {
        it.block()
    }
}
