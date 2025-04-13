repositories {

}

dependencies {
    // Library used for loading libraries in runtime
    api("org.apache.maven:maven-resolver-provider:3.8.5")

    // Logging library
    implementation("org.apache.logging.log4j:log4j-to-slf4j:2.17.1")

    // Library used for managing configurations
    api("org.spongepowered:configurate-yaml:4.2.0")
    api("org.spongepowered:configurate-hocon:4.2.0")
    api("org.spongepowered:configurate-extra-kotlin:4.2.0")

    // Database libraries
    api("com.zaxxer:HikariCP:5.1.0")
    api("com.impossibl.pgjdbc-ng:pgjdbc-ng:0.8.9")
    api("org.mariadb.jdbc:mariadb-java-client:2.1.2")

    // Kyori
    api("net.kyori:adventure-api:4.17.0")
    implementation("net.kyori:adventure-text-serializer-legacy:4.17.0")

    // Gson library
    api("com.google.code.gson:gson:2.11.0")

    // Brigadier library
    api("com.mojang:brigadier:1.2.9")
}
