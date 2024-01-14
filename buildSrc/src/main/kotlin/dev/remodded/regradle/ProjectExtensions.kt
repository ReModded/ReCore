package dev.remodded.regradle

import org.gradle.api.Project
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
