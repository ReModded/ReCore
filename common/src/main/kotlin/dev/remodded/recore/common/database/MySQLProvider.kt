package dev.remodded.recore.common.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.remodded.recore.api.config.DatabaseType
import dev.remodded.recore.api.database.DatabaseProvider
import dev.remodded.recore.common.Constants
import dev.remodded.recore.common.config.DatabaseConnectionConfig
import org.slf4j.LoggerFactory

/**
 * The MySQLProvider class implements the DatabaseProvider interface and provides methods to interact with a MySQL
 * database using HikariCP as the connection pool.
 *
 * The MySQL DataSource is known to be broken with respect to network timeout support.
 * So we are forced to use jdbcUrl configuration instead also that means we can not use AbstractDatabaseProvider
 */
class MySQLProvider(
    private val databaseConnectionConfig: DatabaseConnectionConfig
) : DatabaseProvider {

    private val logger = LoggerFactory.getLogger(Constants.NAME)
    private val dataSource: HikariDataSource

    init {
        logger.info("Initializing MySQL database connection...")

        val config = HikariConfig().apply {
            val hostname = databaseConnectionConfig.hostname
            val port = databaseConnectionConfig.port
            val db = databaseConnectionConfig.database
            jdbcUrl = "jdbc:mysql://$hostname:$port/$db"
            username = databaseConnectionConfig.username
            password = databaseConnectionConfig.password
            poolName = "ReCore"

            maximumPoolSize = databaseConnectionConfig.maxConnectionPoolSize
        }

        dataSource = HikariDataSource(config)
    }

    override fun getDataSource(): HikariDataSource = dataSource

    override fun getType(): DatabaseType = DatabaseType.MYSQL
}
