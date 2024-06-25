package dev.remodded.recore.api.command.source

import dev.remodded.recore.api.entity.Entity
import dev.remodded.recore.api.world.Location

interface CommandSrcStack {
    val sender: CommandSender
    val entity: Entity?
    val location: Location?
}
