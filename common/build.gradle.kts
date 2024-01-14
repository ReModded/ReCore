import dev.remodded.regradle.getPluginProps

plugins {
    id("net.kyori.blossom") version ("1.3.1")
}

repositories {}

dependencies {
    api(project(":api"))
    implementation("org.apache.maven.resolver:maven-resolver-connector-basic:1.7.3")
    implementation("org.apache.maven.resolver:maven-resolver-transport-http:1.7.3")
}

blossom {
    val constants = "src/main/kotlin/dev/remodded/recore/common/Constants.kt"
    getPluginProps().forEach {
        replaceToken("@${it.key}@", it.value, constants)
    }
}

kotlin {
    jvmToolchain(8)
}
