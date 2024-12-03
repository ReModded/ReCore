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
import dev.remodded.recore.api.command.source.ConsoleCommandSender
import dev.remodded.recore.api.plugins.ReCorePlugin
import dev.remodded.recore.api.utils.getFieldAccess
import dev.remodded.recore.common.command.CommonCommandManager
import dev.remodded.recore.paper.command.source.native
import dev.remodded.recore.paper.command.source.wrap
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.PaperCommands
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin


class PaperCommandManager : CommonCommandManager() {

    override fun registerCommand(plugin: ReCorePlugin, command: LiteralArgumentBuilder<CommandSrcStack>, vararg aliases: String) {
        val plugin = plugin.getPluginInfo().mainInstance as Plugin
        val originalCmd = command.build()
        val cmd = wrapCommand(originalCmd) as LiteralCommandNode<CommandSourceStack>
        PaperCommands.INSTANCE.setValid()
        PaperCommands.INSTANCE.register(plugin.pluginMeta, cmd, null, aliases.toList())
        PaperCommands.INSTANCE.invalidate()
    }

    override fun executeCommand(command: String): Int {
        return executeCommand(command, consoleSender())
    }

    override fun executeCommand(command: String, sender: CommandSender): Int {
        return if(Bukkit.getCommandMap().dispatch(sender.native(), command)) 1 else 0
    }

    override fun consoleSender(): ConsoleCommandSender {
        return Bukkit.getConsoleSender().wrap()
    }

    private companion object {
        fun wrapCommand(cmd: CommandNode<CommandSrcStack>): CommandNode<CommandSourceStack> {
            val node = when (cmd) {
                is LiteralCommandNode<*> -> LiteralArgumentBuilder.literal((cmd as LiteralCommandNode<CommandSrcStack>).literal)
                is ArgumentCommandNode<*, *> -> {
                    @Suppress("UNCHECKED_CAST")
                    val arg = cmd as ArgumentCommandNode<CommandSrcStack, Any>
                    val node = RequiredArgumentBuilder.argument<CommandSourceStack, Any>(arg.name, arg.type as ArgumentType<Any>)

                    if (arg.customSuggestions != null)
                        node.suggests { ctx, builder -> arg.listSuggestions(mapCommandCtx(ctx), builder) }

                    node
                }
                else -> throw IllegalArgumentException("Unsupported command node type: ${cmd::class.java}")
            }

            if (cmd.requirement != null)
                node.requires { src -> cmd.requirement.test(src.wrap()) }

            if (cmd.command != null)
                node.executes { ctx -> cmd.command.run(mapCommandCtx(ctx)) }

            for (child in cmd.children) {
                node.then(wrapCommand(child))
            }

            if (cmd.redirect != null)
                throw IllegalArgumentException("Redirects are not supported yet")

            return node.build()
        }

        private fun mapCommandCtx(native: CommandContext<CommandSourceStack>): CommandContext<CommandSrcStack> {
            val source = native.source.wrap()
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
