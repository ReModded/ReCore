
import dev.remodded.regradle.plugin.getPluginProps
import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml

repositories {

}

dependencies {

}

tasks {
    compileKotlin {
        compilerOptions {
//            languageVersion.set(KotlinVersion.KOTLIN_2_2)
        }
    }
}

val props = getPluginProps()

// Override entry point
configure<PaperPluginYaml> {
    main.set("${props.entryPoint}Platform")
    bootstrapper = "${props.entryPoint}Bootstrapper"
}
