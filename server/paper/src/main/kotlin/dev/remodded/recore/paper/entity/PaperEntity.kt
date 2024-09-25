package dev.remodded.recore.paper.entity

import dev.remodded.recore.api.data.additional.AdditionalDataHolder
import dev.remodded.recore.api.entity.Entity
import dev.remodded.recore.api.plugins.ReCorePlugin
import dev.remodded.recore.api.world.Location
import dev.remodded.recore.common.data.additional.CommonAdditionalDataHolder
import dev.remodded.recore.paper.world.PaperLocation
import net.kyori.adventure.text.Component
import java.util.*

open class PaperEntity(
    open val native: org.bukkit.entity.Entity
) : Entity {
    override val id: UUID get() = native.uniqueId
    override val name: Component get() = native.name()
    override val location: Location get() = PaperLocation(native.location)

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
