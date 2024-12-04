package dev.remodded.recore.velocity.command.source

import com.velocitypowered.api.command.CommandSource
import dev.remodded.recore.api.command.source.CommandSender
import dev.remodded.recore.api.command.source.CommandSrcStack
import dev.remodded.recore.api.entity.Entity
import dev.remodded.recore.api.world.Location

class VelocityCommandSourceStack(
    val native: CommandSource,
) : CommandSrcStack {
    override val sender: CommandSender get() = native.wrap()
    override val entity: Entity? = null
    override val location: Location? = null
}

fun CommandSrcStack.native() = (this as VelocityCommandSourceStack).native
