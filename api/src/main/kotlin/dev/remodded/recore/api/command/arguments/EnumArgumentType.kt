package dev.remodded.recore.api.command.arguments

import com.mojang.brigadier.Message
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import com.mojang.brigadier.suggestion.SuggestionProvider
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import dev.remodded.recore.api.command.source.CommandSrcStack
import java.util.concurrent.CompletableFuture
import java.util.function.Supplier

class EnumArgumentType<T: Enum<T>>(
    private val supplier: Supplier<Array<T>>
) : SuggestionProvider<CommandSrcStack> {

    override fun getSuggestions(context: CommandContext<CommandSrcStack>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
        val buildFuture = builder.apply {
            for (value in supplier.get())
                if (value.name.startsWith(builder.remaining, ignoreCase = true))
                    suggest(value.name)
        }.buildFuture()
        return buildFuture
    }

    companion object {
        @JvmStatic
        val ERROR_INVALID_VALUE = DynamicCommandExceptionType { value: Any -> Message{"Invalid enum value: $value"} }

        @JvmStatic
        fun get(): ArgumentType<*> {
            return StringArgumentType.word()
        }

        @JvmStatic
        inline fun <reified T: Enum<T>> enum(): EnumArgumentType<T> {
            return EnumArgumentType { enumValues<T>() }
        }

        @JvmStatic
        inline fun <reified T: Enum<T>> getEnum(ctx: CommandContext<CommandSrcStack>, name: String): T {
            val string = StringArgumentType.getString(ctx, name)
            val values = enumValues<T>()

            return values.find { v -> v.name.equals(string, ignoreCase = true) } ?: throw ERROR_INVALID_VALUE.create(string)
        }
    }
}
