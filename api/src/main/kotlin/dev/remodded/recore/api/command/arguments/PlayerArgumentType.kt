package dev.remodded.recore.api.command.arguments

import com.mojang.brigadier.LiteralMessage
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import dev.remodded.recore.api.command.arguments.resolvers.PlayerSelectorArgumentResolver
import dev.remodded.recore.api.command.source.CommandSrcStack

interface PlayerArgumentType : ArgumentType<PlayerSelectorArgumentResolver> {
    companion object {
        private val PLAYER_NOT_FOUND = DynamicCommandExceptionType { value -> LiteralMessage("Player $value not found") }
        private val NO_PLAYERS_MATCH = DynamicCommandExceptionType { value -> LiteralMessage("No players matched '$value'") }

        fun player() = PlatformArgumentTypesProvider.player(false)
        fun players() = PlatformArgumentTypesProvider.player(true)

        fun getPlayer(ctx: CommandContext<CommandSrcStack>, name: String) =
            getPlayers(ctx, name)[0]

        fun getPlayers(ctx: CommandContext<CommandSrcStack>, name: String) =
            ctx.getArgument(name, PlayerSelectorArgumentResolver::class.java).resolve(ctx.source)
    }
}
