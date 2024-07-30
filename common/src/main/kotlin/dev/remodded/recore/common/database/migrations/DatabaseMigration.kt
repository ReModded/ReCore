package dev.remodded.recore.common.database.migrations

import java.io.InputStream
import java.util.function.Supplier

data class DatabaseMigration(
    val filename: String,
    val timestamp: Long,
    val inputStreamProvider: Supplier<InputStream>,
)
