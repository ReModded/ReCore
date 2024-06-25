import dev.remodded.regradle.ReGradlePlugin

apply<ReGradlePlugin>()

repositories {}

dependencies {
    api(project(":api"))
    implementation("org.apache.maven.resolver:maven-resolver-connector-basic:1.7.3")
    implementation("org.apache.maven.resolver:maven-resolver-transport-http:1.7.3")
}
