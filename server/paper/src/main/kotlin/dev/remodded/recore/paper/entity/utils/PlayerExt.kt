package dev.remodded.recore.paper.entity.utils

import dev.remodded.recore.api.entity.Player
import dev.remodded.recore.paper.entity.PaperPlayer
import org.bukkit.craftbukkit.entity.CraftPlayer

fun Player.native() = (this as PaperPlayer).native
fun org.bukkit.entity.Player.wrap() = PaperPlayer(this as CraftPlayer)
