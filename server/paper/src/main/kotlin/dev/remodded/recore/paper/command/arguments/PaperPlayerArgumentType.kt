package dev.remodded.recore.paper.command.arguments

import dev.remodded.recore.api.command.arguments.PlayerArgumentType
import dev.remodded.recore.api.command.arguments.resolvers.PlayerSelectorArgumentResolver
import dev.remodded.recore.paper.command.arguments.PaperArgumentTypesProvider.PaperWrappedArgumentType
import dev.remodded.recore.paper.command.source.native
import dev.remodded.recore.paper.entity.wrap
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver as PaperPlayerSelectorArgumentResolver

class PaperPlayerArgumentType(
    allowMultiple: Boolean
) :
    PaperWrappedArgumentType<PaperPlayerSelectorArgumentResolver, PlayerSelectorArgumentResolver>(
        if (allowMultiple) ArgumentTypes.players() else ArgumentTypes.player()
    ),
    PlayerArgumentType
{
    override fun convert(nativeType: PaperPlayerSelectorArgumentResolver): PlayerSelectorArgumentResolver {
        return PlayerSelectorArgumentResolver {
            nativeType.resolve(it.native()).map { it.wrap() }
        }
    }
}
