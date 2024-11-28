repositories {

}

dependencies {
    implementation("org.apache.maven.resolver:maven-resolver-connector-basic:1.7.3")
    implementation("org.apache.maven.resolver:maven-resolver-transport-http:1.7.3")

    implementation("org.redisson:redisson:3.32.0")

    testImplementation(kotlin("test"))
}
