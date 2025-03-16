package dev.remodded.recore.api.command.arguments.resolvers

import com.mojang.brigadier.exceptions.CommandSyntaxException
import dev.remodded.recore.api.command.source.CommandSrcStack

fun interface ArgumentResolver<T> {
    /**
     * Resolves the argument with the given
     * command source stack.
     * @param src command source stack
     * @return resolved
     */
    @Throws(CommandSyntaxException::class)
    fun resolve(src: CommandSrcStack): T
}
