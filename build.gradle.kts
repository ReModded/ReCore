import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin
import dev.remodded.regradle.*
import org.jetbrains.dokka.gradle.DokkaPlugin
import java.util.*

plugins {
    kotlin("jvm") version "1.9.20"
    application
    `maven-publish`
    id("org.jetbrains.dokka") version "1.9.0"
    id("com.github.johnrengelman.shadow") version ("8.1.+")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.withType<Jar> {
    enabled = false
}

subprojects {
    apply<MavenPublishPlugin>()
    apply<DokkaPlugin>()
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply<ShadowPlugin>()

    val props = getPluginProps()

    group = props["group"]!!
    version = props["version"]!!

    val dokkaOutputDir = "${project.buildDir}/dokka"

    val javadocJar by tasks.registering(Jar::class) {
        dependsOn(tasks.dokkaHtml)
        archiveClassifier.set("javadoc")
        archiveAppendix.set(project.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
        archiveBaseName.set(props["name"])
        from(dokkaOutputDir)
    }

    val sourceJar by tasks.registering(Jar::class) {
        archiveClassifier.set("sources")
        archiveAppendix.set(project.getProjectSuffix())
        archiveBaseName.set(props["name"])
        from(sourceSets.main.get().allSource)
    }

    tasks {
        dokkaHtml {
            outputDirectory.set(file(dokkaOutputDir))
        }

        kotlin {
            jvmToolchain(17)
        }

        assemble {
            dependsOn(shadowJar)
        }

        jar {
            if (needsShadow()) {
                archiveClassifier.set("clean")
            }
        }

        shadowJar {
            if (!needsShadow()) {
                enabled = false
            }
            archiveClassifier.set("")
            archiveAppendix.set(project.getProjectSuffix())
            archiveBaseName.set(props["name"])

            dependencies {
                include {
                    return@include includeInJar(it)
                }
            }

            relocate("org.apache", "lib.org.apache")
            relocate("org.eclipse", "lib.org.eclipse")
            relocate("org.codehaus", "lib.org.codehaus")
            relocate("org.slf4j", "lib.org.slf4j")

            if (isBuildTarget())
                destinationDirectory.set(rootProject.buildDir.resolve("libs"))
        }
    }

    repositories {
        maven("https://repo.remodded.dev/repository/maven-central/")
    }

    dependencies {
        compileOnly(kotlin("stdlib-jdk8"))
    }

    publishing {
        publications {
            create<MavenPublication>(project.name) {
                val username: String? by project
                val password: String? by project

                afterEvaluate {
                    from(components["java"])
                    artifactId = props["name"] + "-" + project.getProjectSuffix()
                    artifact(javadocJar.get())
                    artifact(sourceJar.get())
                }
                repositories {
                    maven {
                        name = "ReModded"
                        url = uri("https://repo.remodded.dev/repository/maven-snapshots/")
                        credentials {
                            this.username = username
                            this.password = password
                        }
                    }
                }
            }
        }
    }
}

tasks {
    build {
        subprojects.forEach { p ->
            dependsOn(p.tasks.build)
        }
    }

    test {
        useJUnitPlatform()
    }

    shadowJar {
        enabled = false
    }
    startShadowScripts {
        enabled = false
    }
}
