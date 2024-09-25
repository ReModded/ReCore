package dev.remodded.recore.paper.command.source

import dev.remodded.recore.api.command.source.CommandSender
import dev.remodded.recore.api.command.source.CommandSrcStack
import dev.remodded.recore.paper.entity.PaperEntity
import dev.remodded.recore.paper.entity.wrap
import dev.remodded.recore.paper.world.PaperLocation
import io.papermc.paper.command.brigadier.CommandSourceStack

class PaperCommandSrcStack(
    val native: CommandSourceStack
) : CommandSrcStack {
    override val sender: CommandSender by lazy { native.sender.wrap() }
    override val entity: PaperEntity? by lazy { native.executor?.wrap() }
    override val location: PaperLocation? by lazy { try { PaperLocation(native.location) } catch (e: Exception) { null } }
}

fun CommandSrcStack.native() = (this as PaperCommandSrcStack).native
fun CommandSourceStack.wrap() = PaperCommandSrcStack(this)
