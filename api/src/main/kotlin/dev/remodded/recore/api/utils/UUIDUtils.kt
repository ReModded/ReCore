@file:JvmName("UUIDUtils")

package dev.remodded.recore.api.utils

import java.util.UUID

fun String.toUUID() = UUID.fromString(this)
fun String.tryToUUID() = try { UUID.fromString(this) } catch (_: IllegalArgumentException) { null }
fun String.isUUID() = tryToUUID() != null

fun CharSequence.toUUID() = this.toString().toUUID()
fun CharSequence.tryToUUID() = this.toString().tryToUUID()
fun CharSequence.isUUID() = this.toString().isUUID()
