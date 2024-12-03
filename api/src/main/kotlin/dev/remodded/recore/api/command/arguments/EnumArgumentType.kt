package dev.remodded.recore.api.command.arguments

import com.mojang.brigadier.Message
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import dev.remodded.recore.api.command.source.CommandSrcStack
import java.util.concurrent.CompletableFuture

class EnumArgumentType<T: Enum<T>>(
    private val enumValues: Collection<T>
) : CustomArgumentType<T, String>(StringArgumentType.word()) {

    override fun convert(string: String): T {
        return enumValues.find { v -> v.name.equals(string, ignoreCase = true) } ?: throw ERROR_INVALID_VALUE.create(string)
    }

    override fun suggest(
        context: CommandContext<CommandSrcStack>,
        builder: SuggestionsBuilder
    ): CompletableFuture<Suggestions> {
        return builder.apply {
            for (value in enumValues)
                if (value.name.startsWith(builder.remaining, ignoreCase = true))
                    suggest(value.name)
        }.buildFuture()
    }

    companion object {
        @JvmStatic
        val ERROR_INVALID_VALUE = DynamicCommandExceptionType { value: Any -> Message{"Invalid enum value: $value"} }

        inline fun <reified T: Enum<T>> enum() = enum(T::class.java)

        @JvmStatic
        fun <T: Enum<T>> enum(clazz: Class<T>) = enum(clazz.enumConstants.toList())

        @JvmStatic
        fun <T: Enum<T>> enum(vararg values: T) = enum(values.toList())

        @JvmStatic
        fun <T: Enum<T>> enum(values: Iterable<T>) = enum(values.toList())

        @JvmStatic
        fun <T: Enum<T>> enum(values: Collection<T>): EnumArgumentType<T> {
            return EnumArgumentType(values)
        }


        inline fun <reified T: Enum<T>> getEnum(ctx: CommandContext<CommandSrcStack>, name: String) = getEnum(ctx, name, T::class.java)

        @JvmStatic
        fun <T: Enum<T>> getEnum(ctx: CommandContext<CommandSrcStack>, name: String, clazz: Class<T>): T {
            return ctx.getArgument(name, clazz)
        }
    }
}
