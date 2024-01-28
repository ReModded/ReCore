package dev.remodded.recore.api.database

import com.zaxxer.hikari.HikariDataSource
import dev.remodded.recore.api.config.DatabaseType
import kotlin.jvm.Throws

/**
 * Interface representing a database provider.
 */
interface DatabaseProvider {

    /**
     * Retrieves the data source object for the database provider.
     *
     * @throws DataSourceNotInitializedException if the data source is not initialized.
     * @return the HikariDataSource object for the database provider.
     */
    @Throws(DataSourceNotInitializedException::class)
    fun getDataSource(): HikariDataSource

    /**
     * Retrieves the type of the database represented by the implementing class.
     *
     * @return the DatabaseType enum representing the type of the database.
     */
    fun getType(): DatabaseType
}
