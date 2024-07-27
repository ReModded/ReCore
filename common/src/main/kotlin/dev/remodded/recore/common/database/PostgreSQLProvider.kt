package dev.remodded.recore.common.database

import dev.remodded.recore.api.database.DatabaseType

/**
 * This class serves as the PostgreSQL database provider for the application.
 * It utilizes HikariCP as the high-performance JDBC connection pool to manage database connections.
 * The database configuration details are provided via an instance of DatabaseConnection during initialization
 *
 * @property databaseConnection Instance of database connection configuration details
 * @constructor creates an instance of dev.remodded.recore.common.database.PostgreSQLProvider and initializes a Hikari connection pool.
 */
class PostgreSQLProvider(
    private val databaseConnection: DatabaseConnectionConfig
) : AbstractDatabaseProvider(
    databaseConnection,
    DatabaseType.POSTGRESQL,
    "com.impossibl.postgres.jdbc.PGDataSource"
)
