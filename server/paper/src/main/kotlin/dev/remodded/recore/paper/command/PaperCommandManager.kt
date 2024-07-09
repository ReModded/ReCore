package dev.remodded.recore.paper.command

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.context.CommandContextBuilder
import com.mojang.brigadier.context.ParsedArgument
import com.mojang.brigadier.tree.ArgumentCommandNode
import com.mojang.brigadier.tree.CommandNode
import com.mojang.brigadier.tree.LiteralCommandNode
import dev.remodded.recore.api.command.source.CommandSender
import dev.remodded.recore.api.command.source.CommandSrcStack
import dev.remodded.recore.api.plugins.PluginInfo
import dev.remodded.recore.api.utils.getFieldAccess
import dev.remodded.recore.common.command.CommonCommandManager
import dev.remodded.recore.paper.command.source.PaperCommandSender
import dev.remodded.recore.paper.command.source.PaperCommandSourceStack
import dev.remodded.recore.paper.entity.PaperEntity
import dev.remodded.recore.paper.world.PaperLocation
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.PaperCommands
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin


@Suppress("UnstableApiUsage")
class PaperCommandManager : CommonCommandManager() {

    override fun registerCommand(pluginInfo: PluginInfo, command: LiteralArgumentBuilder<CommandSrcStack>, vararg aliases: String) {
        val originalCmd = command.build()
        val cmd = wrapCommand(originalCmd) as LiteralCommandNode<CommandSourceStack>
        PaperCommands.INSTANCE.setValid()
        PaperCommands.INSTANCE.setCurrentContext(pluginInfo.mainInstance as Plugin)
        PaperCommands.INSTANCE.register(cmd, aliases.toList())
    }

    override fun executeCommand(command: String): Int {
        return if(Bukkit.getCommandMap().dispatch(Bukkit.getConsoleSender(), command)) 1 else 0
    }

    override fun executeCommand(command: String, sender: CommandSender): Int {
        TODO("Not yet implemented")
    }

    private companion object {
        fun wrapCommand(cmd: CommandNode<CommandSrcStack>): CommandNode<CommandSourceStack> {
            val node = when (cmd) {
                is LiteralCommandNode<*> -> LiteralArgumentBuilder.literal((cmd as LiteralCommandNode<CommandSrcStack>).literal)
                is ArgumentCommandNode<*, *> -> {
                    @Suppress("UNCHECKED_CAST")
                    val arg = cmd as ArgumentCommandNode<CommandSrcStack, Any>
                    val n = RequiredArgumentBuilder.argument<CommandSourceStack, Any>(arg.name, arg.type as ArgumentType<Any>)

                    if (arg.customSuggestions != null)
                        n.suggests { ctx, builder -> arg.listSuggestions(mapCommandCtx(ctx), builder) }

                    n
                }
                else -> throw IllegalArgumentException("Unsupported command node type: ${cmd::class.java}")
            }

            if (cmd.requirement != null)
                node.requires { src -> cmd.requirement.test(mapCommandSourceStack(src)) }

            if (cmd.command != null)
                node.executes { ctx -> cmd.command.run(mapCommandCtx(ctx)) }

            for (child in cmd.children) {
                node.then(wrapCommand(child))
            }

            if (cmd.redirect != null)
                throw IllegalArgumentException("Redirects are not supported yet")

            return node.build()
        }

        private fun mapCommandSourceStack(native: CommandSourceStack): CommandSrcStack {
            return PaperCommandSourceStack(
                PaperCommandSender(native.sender),
                native.executor?.let { PaperEntity(it) },
                try { PaperLocation(native.location) } catch (e: Exception) { null },
            )
        }

        private fun mapCommandCtx(native: CommandContext<CommandSourceStack>): CommandContext<CommandSrcStack> {
            val source = mapCommandSourceStack(native.source)
            @Suppress("UNCHECKED_CAST")
            val arguments = CommandContext::class.java.getFieldAccess("arguments").get(native) as Map<String, ParsedArgument<CommandSrcStack, *>>
            @Suppress("UNCHECKED_CAST")
            return CommandContextBuilder(null, source, native.rootNode as CommandNode<CommandSrcStack>, native.range.start)
                .apply {
                    arguments.forEach { (name, value) -> withArgument(name, value) }
                }
                .build(native.input)
        }
    }
}
