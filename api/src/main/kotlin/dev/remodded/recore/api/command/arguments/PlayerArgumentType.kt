package dev.remodded.recore.api.command.arguments

import com.mojang.brigadier.LiteralMessage
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import dev.remodded.recore.api.ReCore
import dev.remodded.recore.api.command.source.CommandSrcStack
import dev.remodded.recore.api.entity.Player
import java.util.concurrent.CompletableFuture

class PlayerArgumentType(
    val sigular: Boolean,
) : CustomArgumentType<Collection<Player>, String>(StringArgumentType.word()) {

    override fun convert(nativeType: String): Collection<Player> {
        if (nativeType == "@a" && !sigular) {
            val players = ReCore.INSTANCE.server.playerManager.getPlayers()

            if (players.isEmpty())
                throw NO_PLAYERS_MATCH.create(nativeType)

            return players
        }

        return listOf(ReCore.INSTANCE.server.playerManager.getPlayer(nativeType) ?: throw PLAYER_NOT_FOUND.create(nativeType))
    }

    override fun suggest(
        context: CommandContext<CommandSrcStack>,
        builder: SuggestionsBuilder
    ): CompletableFuture<Suggestions> = builder
        .apply {
            if (!sigular)
                suggest("@a")

            for (player in ReCore.INSTANCE.server.playerManager.getPlayers())
                suggest(player.name)
        }
        .buildFuture()


    companion object {
        private val PLAYER_NOT_FOUND = DynamicCommandExceptionType { value -> LiteralMessage("Player $value not found") }
        private val NO_PLAYERS_MATCH = DynamicCommandExceptionType { value -> LiteralMessage("No players matched '$value'") }

        fun player() = PlayerArgumentType(true)
        fun players() = PlayerArgumentType(false)

        fun getPlayers(ctx: CommandContext<CommandSrcStack>, name: String) =
            ctx.getArgument(name, List::class.java).filterIsInstance<Player>()

        fun getPlayer(ctx: CommandContext<CommandSrcStack>, name: String) =
            ctx.getArgument(name, List::class.java)[0] as Player
    }
}
