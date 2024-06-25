package dev.remodded.recore.paper.command.source

import dev.remodded.recore.api.command.source.CommandSrcStack
import dev.remodded.recore.paper.entity.PaperEntity
import dev.remodded.recore.paper.world.PaperLocation

class PaperCommandSourceStack(
    override val sender: PaperCommandSender,
    override val entity: PaperEntity?,
    override val location: PaperLocation?
) : CommandSrcStack
