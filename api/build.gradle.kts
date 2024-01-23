repositories {
}

dependencies {
    api("org.apache.maven:maven-resolver-provider:3.8.5")
    api("org.spongepowered:configurate-hocon:4.1.2")
    api("org.spongepowered:configurate-extra-kotlin:4.1.2")
}

kotlin {
    jvmToolchain(8)
}
