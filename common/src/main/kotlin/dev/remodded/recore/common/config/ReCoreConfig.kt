package dev.remodded.recore.common.config

import dev.remodded.recore.api.config.DatabaseType
import dev.remodded.recore.api.messaging.MessagingChannelType
import dev.remodded.recore.common.connections.redis.RedisConfig
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.meta.Comment

@ConfigSerializable
class ReCoreConfig {

    @Comment(
        """
Specify the messaging service used for communication between plugins. Valid options are:

CHANNELS - use default minecraft messaging channels (no external services required). It requires at least one
player connected to the server for it to work.

REDIS - use redis as messaging channel, it is preferred option. When using redis as messaging service you must 
provide connection details to redis server.

POSTGRES - use postgres as messaging service. When using this option you must provide postgresql connection
details to postgresql database.
    """
    )
    val messagingService: MessagingChannelType = MessagingChannelType.CHANNELS

    @Comment("Database connection details")
    val database = DatabaseConnectionConfig()

    @Comment("Redis connection details")
    val redis = RedisConfig()
}

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
