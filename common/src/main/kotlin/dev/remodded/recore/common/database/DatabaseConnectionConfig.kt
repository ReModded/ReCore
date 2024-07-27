package dev.remodded.recore.common.database

import dev.remodded.recore.api.database.DatabaseType
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.meta.Comment

@ConfigSerializable
class DatabaseConnectionConfig {

    @Comment(
        "Database type\n\n" +

                "POSTGRESQL - preferred option\n\n" +
                "MARIADB\n" +
                "MYSQL\n"
    )
    val databaseType: DatabaseType = DatabaseType.POSTGRESQL

    @Comment("Database instance host or ip address")
    val hostname: String = "localhost"

    @Comment(
        "Database port\n\n" +

                "POSTGRESQL - 5432\n" +
                "MYSQL / MARIADB - 3306"
    )
    val port: Int = 5432

    @Comment("Username")
    val username: String = "Me"

    @Comment("Password")
    val password: String = "database password"

    @Comment("Database name")
    val database: String = "Database"

    @Comment("Max number of connection that server can open to the database")
    val maxConnectionPoolSize: Int = 4
}
