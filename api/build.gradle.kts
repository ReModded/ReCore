repositories {
}

dependencies {
    // Library used for loading libraries in runtime
    api("org.apache.maven:maven-resolver-provider:3.8.5")

    // Logging library
    implementation("org.apache.logging.log4j:log4j-to-slf4j:2.17.1")

    // Library used for managing configurations
    api("org.spongepowered:configurate-hocon:4.1.2")
    api("org.spongepowered:configurate-extra-kotlin:4.1.2")

    // Database libraries
    api("com.zaxxer:HikariCP:5.1.0")
    api("com.impossibl.pgjdbc-ng:pgjdbc-ng:0.8.9")
    api("org.mariadb.jdbc:mariadb-java-client:2.1.2")

    // Brigadier library
    api("com.mojang:brigadier:1.2.9")
}
