package dev.remodded.recore.paper.command

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.context.CommandContextBuilder
import com.mojang.brigadier.context.ParsedArgument
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import com.mojang.brigadier.tree.ArgumentCommandNode
import com.mojang.brigadier.tree.CommandNode
import com.mojang.brigadier.tree.LiteralCommandNode
import dev.remodded.recore.api.command.arguments.CustomArgumentType
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
import net.minecraft.commands.synchronization.ArgumentTypeInfos
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import java.util.concurrent.CompletableFuture


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
            @Suppress("UNCHECKED_CAST")
            val node = when (cmd) {
                is LiteralCommandNode<*> -> LiteralArgumentBuilder.literal((cmd as LiteralCommandNode<CommandSrcStack>).literal)
                is ArgumentCommandNode<*, *> -> {
                    @Suppress("UNCHECKED_CAST")
                    val argumentNode = cmd as ArgumentCommandNode<CommandSrcStack, Any>
                    var argumentType = argumentNode.type

                    val arg = argumentType
                    if (arg is CustomArgumentType<*, *>) {
                        argumentType = object : io.papermc.paper.command.brigadier.argument.CustomArgumentType.Converted<Any, Any> {
                            override fun getNativeType() = arg.nativeType as ArgumentType<Any>
                            override fun convert(nativeType: Any): Any {
                                return (arg as CustomArgumentType<Any, Any>).convert(nativeType)
                            }

                            override fun <S : Any> listSuggestions(
                                context: CommandContext<S>,
                                builder: SuggestionsBuilder
                            ): CompletableFuture<Suggestions> {
                                return arg.suggest(mapCommandCtx(context as CommandContext<CommandSourceStack>), builder)
                            }
                        }
                    }
                    else if (!ArgumentTypeInfos.isClassRecognized(argumentType.javaClass))
                        throw IllegalArgumentException("Unsupported custom argument type: ${argumentType::class.simpleName}")

                    @Suppress("UNCHECKED_CAST")
                    RequiredArgumentBuilder.argument<CommandSourceStack, Any>(argumentNode.name, argumentType).apply {
                        if (argumentNode.customSuggestions != null)
                            suggests { ctx, builder -> argumentNode.customSuggestions.getSuggestions(mapCommandCtx(ctx), builder) }
                    }
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

        @Suppress("UNCHECKED_CAST")
        private fun mapCommandCtx(native: CommandContext<CommandSourceStack>): CommandContext<CommandSrcStack> {
            val source = native.source.wrap()
            val arguments = CommandContext::class.java.getFieldAccess("arguments").get(native) as Map<String, ParsedArgument<CommandSrcStack, *>>
            return CommandContextBuilder(null, source, native.rootNode as CommandNode<CommandSrcStack>, native.range.start)
                .apply {
                    arguments.forEach { (name, value) -> withArgument(name, value) }
                }
                .build(native.input)
        }
    }
}
