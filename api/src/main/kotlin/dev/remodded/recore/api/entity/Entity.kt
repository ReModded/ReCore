package dev.remodded.recore.api.entity

import dev.remodded.recore.api.world.Location
import net.kyori.adventure.text.Component
import java.util.*

interface Entity {
    val id: UUID
    val name: Component

    val location: Location
}
