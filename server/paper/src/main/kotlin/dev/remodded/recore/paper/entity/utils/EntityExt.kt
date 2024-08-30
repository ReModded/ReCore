package dev.remodded.recore.paper.entity.utils

import dev.remodded.recore.api.entity.Entity
import dev.remodded.recore.paper.entity.PaperEntity

fun Entity.native() = (this as PaperEntity).native
fun org.bukkit.entity.Entity.wrap() = when(this) {
    is org.bukkit.entity.Player -> this.wrap()
    else -> PaperEntity(this)
}
