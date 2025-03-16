package dev.remodded.recore.paper.command.arguments

import dev.remodded.recore.api.command.arguments.ResourceKeyArgumentType
import dev.remodded.recore.api.resources.ResourceKey
import dev.remodded.recore.paper.command.arguments.PaperArgumentTypesProvider.PaperWrappedArgumentType
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import org.bukkit.NamespacedKey

class PaperResourceKeyArgumentType :
    PaperWrappedArgumentType<NamespacedKey, ResourceKey>(ArgumentTypes.namespacedKey()),
    ResourceKeyArgumentType
{
    override fun convert(nativeType: NamespacedKey): ResourceKey {
        return ResourceKey.of(nativeType.namespace, nativeType.key)
    }
}
