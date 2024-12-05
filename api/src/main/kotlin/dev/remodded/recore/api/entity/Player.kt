package dev.remodded.recore.api.entity

import dev.remodded.recore.api.command.source.CommandSender
import net.kyori.adventure.text.Component

interface Player : Entity, CommandSender {
    val gamemode: GameMode

    fun kick(message: Component? = null)
}
