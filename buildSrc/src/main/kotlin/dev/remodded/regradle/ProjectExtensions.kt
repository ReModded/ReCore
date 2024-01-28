package dev.remodded.regradle

import org.gradle.api.Project
import org.gradle.api.artifacts.ResolvedDependency
import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.support.uppercaseFirstChar
import java.util.*

fun Project.getPluginProps(): Map<String, String> {
    val props = this.properties
        .filter { prop -> prop.key.startsWith('$') }
        .map { prop -> prop.key.drop(1) to prop.value as String }
        .toMap(mutableMapOf())

    val name = props["name"] ?: "ReTemplate"
    val platform = this.name.uppercaseFirstChar()
    val group = props["group"] ?: "dev.remodded"

    val mainClassname =
        "$name${if (platform.contains('-')) platform.substring(0, platform.lastIndexOf('-')) else platform}"

    props["entry_point"] = "$group.${name.lowercase()}.${platform.lowercase().replace('-', '_')}.$mainClassname"

    return props
}

fun <T : Any> ExtraPropertiesExtension.getOptional(name: String): T? {
    @Suppress("UNCHECKED_CAST")
    return if (has(name)) get(name) as? T else null
}


private const val BUILD_TARGET_NAME_ID = "isBuildTarget"
fun Project.markAsBuildTarget() {
    this.extra[BUILD_TARGET_NAME_ID] = true
}

fun Project.isBuildTarget(): Boolean {
    return this.extra.getOptional(BUILD_TARGET_NAME_ID) ?: false
}


private const val NEEDS_SHADOW = "needsShadow"
fun Project.markAsNeedShadow() {
    this.extra[NEEDS_SHADOW] = true
}

fun Project.needsShadow(): Boolean {
    return this.extra.getOptional(NEEDS_SHADOW) ?: false
}

fun Project.getProjectSuffix(): String {
    val name = project.name.replace("api", "API")
    return project.name.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }
}

fun Project.includeInJar(dependency: ResolvedDependency): Boolean {
    return dependencyList.any {
        dependency.name.contains(it)
    }
}

private val dependencyList = arrayListOf(
    "commons-codec:commons-codec",
    "dev.remodded:api",
    "dev.remodded:common",
    "dev.remodded:proxy",
    "dev.remodded:server",
    "javax.inject:javax.inject",
    "org.apache.commons:commons-lang3",
    "org.apache.httpcomponents:httpclient",
    "org.apache.httpcomponents:httpcore",
    "org.apache.maven.resolver:maven-resolver-api",
    "org.apache.maven.resolver:maven-resolver-api",
    "org.apache.maven.resolver:maven-resolver-connector-basic",
    "org.apache.maven.resolver:maven-resolver-impl",
    "org.apache.maven.resolver:maven-resolver-spi",
    "org.apache.maven.resolver:maven-resolver-spi",
    "org.apache.maven.resolver:maven-resolver-transport-http",
    "org.apache.maven.resolver:maven-resolver-util",
    "org.apache.maven.resolver:maven-resolver-util",
    "org.apache.maven:maven-artifact",
    "org.apache.maven:maven-builder-support",
    "org.apache.maven:maven-model-builder",
    "org.apache.maven:maven-model",
    "org.apache.maven:maven-repository-metadata",
    "org.apache.maven:maven-resolver-provider",
    "org.codehaus.plexus:plexus-interpolation",
    "org.codehaus.plexus:plexus-utils",
    "org.eclipse.sisu:org.eclipse.sisu.inject",
    "org.slf4j:jcl-over-slf4j",
    "org.slf4j:slf4j-api",
    "org.slf4j.impl",
)
