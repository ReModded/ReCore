package dev.remodded.recore.paper.command.arguments

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import dev.remodded.recore.api.command.arguments.CustomArgumentType
import dev.remodded.recore.api.command.arguments.PlatformArgumentTypesProvider
import dev.remodded.recore.api.command.source.CommandSrcStack
import java.util.concurrent.CompletableFuture

class PaperArgumentTypesProvider : PlatformArgumentTypesProvider {
    override fun player(allowMultiple: Boolean) = PaperPlayerArgumentType(allowMultiple)

    abstract class PaperWrappedArgumentType<NativeType, ParsedType>(
        nativeType: ArgumentType<NativeType>
    ) : CustomArgumentType<ParsedType, NativeType>(nativeType) {
        final override fun suggest(context: CommandContext<CommandSrcStack>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
            return nativeType.listSuggestions(context, builder)
        }

        open fun doMapCtx(): Boolean = false
    }
}
