package dev.remodded.recore.velocity.command

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.context.CommandContextBuilder
import com.mojang.brigadier.context.ParsedArgument
import com.mojang.brigadier.tree.ArgumentCommandNode
import com.mojang.brigadier.tree.CommandNode
import com.mojang.brigadier.tree.LiteralCommandNode
import com.velocitypowered.api.command.BrigadierCommand
import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.proxy.ProxyServer
import dev.remodded.recore.api.command.source.CommandSender
import dev.remodded.recore.api.command.source.CommandSrcStack
import dev.remodded.recore.api.command.source.ConsoleCommandSender
import dev.remodded.recore.api.plugins.ReCorePlugin
import dev.remodded.recore.api.utils.getFieldAccess
import dev.remodded.recore.common.command.CommonCommandManager
import dev.remodded.recore.velocity.command.source.VelocityCommandSourceStack
import dev.remodded.recore.velocity.command.source.native
import dev.remodded.recore.velocity.command.source.wrap

class VelocityCommandManager(private val proxy: ProxyServer) : CommonCommandManager() {

    override fun registerCommand(plugin: ReCorePlugin, command: LiteralArgumentBuilder<CommandSrcStack>, vararg aliases: String) {
        val cmd = BrigadierCommand(wrapCommand(command.build()) as LiteralCommandNode<CommandSource>)

        val meta = proxy.commandManager.metaBuilder(cmd).aliases(*aliases).build()

        proxy.commandManager.register(meta, cmd)
    }

    override fun executeCommand(command: String): Int {
        return executeCommand(command, consoleSender())
    }

    override fun executeCommand(command: String, sender: CommandSender): Int {
        return if (proxy.commandManager.executeImmediatelyAsync(sender.native(), command).get()) 1 else 0
    }

    override fun consoleSender(): ConsoleCommandSender {
        return proxy.consoleCommandSource.wrap()
    }

    private companion object {
        fun wrapCommand(cmd: CommandNode<CommandSrcStack>): CommandNode<CommandSource> {
            val node = when (cmd) {
                is LiteralCommandNode<*> -> LiteralArgumentBuilder.literal((cmd as LiteralCommandNode<CommandSrcStack>).literal)
                is ArgumentCommandNode<*, *> -> {
                    @Suppress("UNCHECKED_CAST")
                    val arg = cmd as ArgumentCommandNode<CommandSrcStack, Any>
                    val node = RequiredArgumentBuilder.argument<CommandSource, Any>(arg.name, arg.type as ArgumentType<Any>)

                    if (arg.customSuggestions != null)
                        node.suggests { ctx, builder -> arg.listSuggestions(mapCommandCtx(ctx), builder) }

                    node
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

        private fun mapCommandSourceStack(native: CommandSource): CommandSrcStack {
            return VelocityCommandSourceStack(native)
        }

        private fun mapCommandCtx(native: CommandContext<CommandSource>): CommandContext<CommandSrcStack> {
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
