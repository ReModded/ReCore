package dev.remodded.recore.api.database

/**
 * Exception thrown when the data source for a specific database type is not initialized.
 *
 * @param databaseType The type of the uninitialized data source.
 * @constructor Creates a new instance of the exception with the given database type.
 */
class DataSourceNotInitializedException(
    databaseType: DatabaseType
): Exception("Data source ${databaseType.name} not initialized!")
