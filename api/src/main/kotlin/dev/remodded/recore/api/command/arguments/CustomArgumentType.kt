package dev.remodded.recore.api.command.arguments

import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import dev.remodded.recore.api.command.source.CommandSrcStack
import java.util.concurrent.CompletableFuture

abstract class CustomArgumentType<T, N>(
    val nativeType: ArgumentType<N>,
) : ArgumentType<T> {

    abstract fun convert(nativeType: N): T

    open fun suggest(context: CommandContext<CommandSrcStack>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
        return super.listSuggestions(context, builder)
    }


    final override fun getExamples(): Collection<String> {
        return nativeType.examples
    }

    final override fun <S : Any> listSuggestions(
        context: CommandContext<S>,
        builder: SuggestionsBuilder
    ): CompletableFuture<Suggestions> {
        return super.listSuggestions(context, builder)
    }

    final override fun parse(reader: StringReader): T {
        return convert(nativeType.parse(reader))
    }
}
