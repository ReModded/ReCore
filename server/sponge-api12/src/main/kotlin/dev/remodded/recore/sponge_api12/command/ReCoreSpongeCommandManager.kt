package dev.remodded.recore.sponge_api12.command

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import dev.remodded.recore.api.plugins.PluginInfo
import dev.remodded.recore.api.command.source.CommandSender
import dev.remodded.recore.api.command.source.CommandSrcStack
import dev.remodded.recore.common.command.CommonCommandManager
import io.leangen.geantyref.TypeToken
import net.minecraft.commands.CommandSourceStack
import org.spongepowered.api.Sponge
import org.spongepowered.common.command.registrar.BrigadierCommandRegistrar
import java.util.*

class ReCoreSpongeCommandManager : CommonCommandManager() {
    private val registrar = Sponge.server().commandManager().registrar(object : TypeToken<LiteralArgumentBuilder<CommandSourceStack>>(){}).let {
        if (it !is Optional<*>)
            throw IllegalStateException("Failed to get BrigadierCommandRegistrar. It is not Optional")
        if (!it.isPresent)
            throw IllegalStateException("Failed to get BrigadierCommandRegistrar. It is not present")
        if (it.get() !is BrigadierCommandRegistrar)
            throw IllegalStateException("Failed to get BrigadierCommandRegistrar. It's not BrigadierCommandRegistrar type. Got ${it.get().javaClass.name} instead.")
        it.get() as BrigadierCommandRegistrar
    }

    override fun registerCommand(pluginInfo: PluginInfo, command: LiteralArgumentBuilder<CommandSrcStack>, vararg aliases: String) {
        @Suppress("UNCHECKED_CAST")
        registrar.register(null, command as LiteralArgumentBuilder<CommandSourceStack>, *aliases)
    }

    override fun executeCommand(command: String): Int {
        TODO("Not yet implemented")
    }

    override fun executeCommand(command: String, sender: CommandSender): Int {
        TODO("Not yet implemented")
    }
}
