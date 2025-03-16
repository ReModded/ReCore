package dev.remodded.recore.api.command.arguments

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import dev.remodded.recore.api.command.source.CommandSrcStack
import dev.remodded.recore.api.resources.ResourceKey

interface ResourceKeyArgumentType : ArgumentType<ResourceKey>  {
    companion object {
        fun resoruceKey() = PlatformArgumentTypesProvider.resourceKey()

        fun getResourceKey(ctx: CommandContext<CommandSrcStack>, name: String): ResourceKey {
            return ctx.getArgument(name, ResourceKey::class.java)
        }
    }
}
