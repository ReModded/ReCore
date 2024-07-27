package dev.remodded.recore.common.database

import dev.remodded.recore.api.database.DatabaseType

class MariaDBProvider(
    databaseConnection: DatabaseConnectionConfig
) : AbstractDatabaseProvider(
    databaseConnection,
    DatabaseType.MARIADB,
    "org.mariadb.jdbc.MariaDbDataSource"
)
