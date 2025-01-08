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
import dev.remodded.recore.api.command.arguments.CustomArgumentType
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
        val cmd = BrigadierCommand(wrapCommand(command.build(), mutableMapOf()) as LiteralCommandNode<CommandSource>)

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
        fun wrapCommand(cmd: CommandNode<CommandSrcStack>, customArgs: MutableMap<String, CustomArgumentType<*, *>>): CommandNode<CommandSource> {
            var cArgs = LinkedHashMap(customArgs)
            val node = when (cmd) {
                is LiteralCommandNode<*> -> LiteralArgumentBuilder.literal((cmd as LiteralCommandNode<CommandSrcStack>).literal)
                is ArgumentCommandNode<*, *> -> {
                    @Suppress("UNCHECKED_CAST")
                    val argumentNode = cmd as ArgumentCommandNode<CommandSrcStack, Any>
                    var argumentType = argumentNode.type


                    val arg = argumentType
                    if (arg is CustomArgumentType<*, *>) {
                        cArgs[cmd.name] = arg

                        @Suppress("UNCHECKED_CAST")
                        argumentType = arg.nativeType as ArgumentType<Any>
                    } //else if (ArgumentPropertyRegistry.)

                    @Suppress("UNCHECKED_CAST")
                    RequiredArgumentBuilder.argument<CommandSource, Any>(argumentNode.name, argumentType).apply {
                        if (arg is CustomArgumentType<*, *>) {
                            if (argumentNode.customSuggestions != null)
                                throw IllegalArgumentException("Custom argument type cannot have custom suggestions")

                            suggests { ctx, builder -> arg.suggest(mapCommandCtx(ctx, cArgs), builder) }

                        } else if (argumentNode.customSuggestions != null)
                            suggests { ctx, builder -> argumentNode.customSuggestions.getSuggestions(mapCommandCtx(ctx, cArgs), builder) }
                    }
                }
                else -> throw IllegalArgumentException("Unsupported command node type: ${cmd::class.java}")
            }

            if (cmd.requirement != null)
                node.requires { src -> cmd.requirement.test(mapCommandSourceStack(src)) }

            if (cmd.command != null)
                node.executes { ctx -> cmd.command.run(mapCommandCtx(ctx, cArgs)) }

            for (child in cmd.children) {
                node.then(wrapCommand(child, cArgs))
            }

            if (cmd.redirect != null)
                throw IllegalArgumentException("Redirects are not supported yet")

            return node.build()
        }

        private fun mapCommandSourceStack(native: CommandSource): CommandSrcStack {
            return VelocityCommandSourceStack(native)
        }

        @Suppress("UNCHECKED_CAST")
        private fun mapCommandCtx(native: CommandContext<CommandSource>, customArgs: MutableMap<String, CustomArgumentType<*, *>>): CommandContext<CommandSrcStack> {
            val source = mapCommandSourceStack(native.source)
            val arguments = CommandContext::class.java.getFieldAccess("arguments").get(native) as Map<String, ParsedArgument<CommandSrcStack, Any>>
            return CommandContextBuilder(null, source, native.rootNode as CommandNode<CommandSrcStack>, native.range.start)
                .apply {
                    for ((name, value) in arguments) {
                        if (customArgs.containsKey(name)) {
                            val arg = customArgs[name]!! as CustomArgumentType<Any, Any>
                            val resultField = ParsedArgument::class.java.getFieldAccess("result")
                            resultField.set(value, arg.convert(value.result))
                        }
                        withArgument(name, value)
                    }
                }
                .build(native.input)
        }
    }
}
