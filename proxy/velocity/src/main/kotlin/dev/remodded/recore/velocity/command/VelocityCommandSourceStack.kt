package dev.remodded.recore.velocity.command

import dev.remodded.recore.api.command.source.CommandSrcStack
import dev.remodded.recore.api.entity.Entity
import dev.remodded.recore.api.world.Location

class VelocityCommandSourceStack(
    override val sender: VelocityCommandSender,
) : CommandSrcStack {
    override val entity: Entity? = null
    override val location: Location? = null
}
