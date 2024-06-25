package dev.remodded.recore.api.entity

import dev.remodded.recore.api.world.Location
import java.util.*

interface Entity {
    val id: UUID
    val name: String

    val location: Location
}
