package dev.remodded.recore.common.database

import dev.remodded.recore.api.config.DatabaseType

class MariaDBProvider(
    private val databaseConnection: DatabaseConnectionConfig
) : AbstractDatabaseProvider(
    databaseConnection,
    DatabaseType.MARIADB,
    "org.mariadb.jdbc.MariaDbDataSource"
)
