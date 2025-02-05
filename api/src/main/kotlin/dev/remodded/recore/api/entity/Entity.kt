package dev.remodded.recore.api.entity

import dev.remodded.recore.api.data.additional.AdditionalDataHolder
import dev.remodded.recore.api.world.Location
import net.kyori.adventure.text.Component
import java.util.*

interface Entity : AdditionalDataHolder {
    val id: UUID
    val name: String
    val displayName: Component

    val location: Location

    fun teleport(location: Location)
}
