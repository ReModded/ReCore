repositories {

}

dependencies {

}

tasks {
    jar {
        manifest {
            attributes(
                "MixinConfigs" to "mixins.recore.json"
            )
        }
    }
}
