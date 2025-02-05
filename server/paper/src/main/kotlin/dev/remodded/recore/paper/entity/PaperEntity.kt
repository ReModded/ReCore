package dev.remodded.recore.paper.entity

import dev.remodded.recore.api.data.additional.AdditionalDataHolder
import dev.remodded.recore.api.entity.Entity
import dev.remodded.recore.api.plugins.ReCorePlugin
import dev.remodded.recore.api.world.Location
import dev.remodded.recore.common.data.additional.CommonAdditionalDataHolder
import dev.remodded.recore.paper.world.PaperLocation
import dev.remodded.recore.paper.world.toNative
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import java.util.*

open class PaperEntity(
    open val native: org.bukkit.entity.Entity
) : Entity {
    override val id: UUID get() = native.uniqueId
    override val name: String get() = PlainTextComponentSerializer.plainText().serialize(native.name())
    override val displayName: Component get() = native.name()
    override val location: Location get() = PaperLocation(native.location)

    override fun teleport(location: Location) {
        native.teleport(location.toNative())
    }

    // Additional Data Holder delegation
    val additionalDataHolderDelegate: AdditionalDataHolder by lazy { CommonAdditionalDataHolder(this) }
    override fun getAllAdditionalData() = additionalDataHolderDelegate.getAllAdditionalData()
    override fun getAdditionalData(plugin: ReCorePlugin) = additionalDataHolderDelegate.getAdditionalData(plugin)
    override fun removeAdditionalData(plugin: ReCorePlugin) = additionalDataHolderDelegate.removeAdditionalData(plugin)
    override fun clearAdditionalData() = additionalDataHolderDelegate.clearAdditionalData()
}

fun Entity.native() = (this as PaperEntity).native
fun org.bukkit.entity.Entity.wrap() = when(this) {
    is org.bukkit.entity.Player -> this.wrap()
    else -> PaperEntity(this)
}
