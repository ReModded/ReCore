package dev.remodded.recore.paper.command.source.utils

import dev.remodded.recore.api.command.source.CommandSrcStack
import dev.remodded.recore.paper.command.source.PaperCommandSrcStack
import io.papermc.paper.command.brigadier.CommandSourceStack

fun CommandSrcStack.native() = (this as PaperCommandSrcStack).native
fun CommandSourceStack.wrap() = PaperCommandSrcStack(this)
