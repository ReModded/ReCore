package dev.remodded.recore.api.entity

import dev.remodded.recore.api.command.source.CommandSender
import dev.remodded.recore.api.extention.Extendable
import net.kyori.adventure.text.Component

interface Player : Entity, CommandSender, Extendable {
    val gamemode: GameMode

    fun kick(message: Component? = null)
}
