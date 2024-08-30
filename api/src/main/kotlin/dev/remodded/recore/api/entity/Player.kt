package dev.remodded.recore.api.entity

import dev.remodded.recore.api.command.source.CommandSender

interface Player : Entity, CommandSender {
    val gamemode: GameMode
}
