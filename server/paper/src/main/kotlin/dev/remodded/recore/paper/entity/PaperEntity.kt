package dev.remodded.recore.paper.entity

import dev.remodded.recore.api.entity.Entity
import dev.remodded.recore.api.world.Location
import dev.remodded.recore.paper.world.PaperLocation
import net.kyori.adventure.text.Component
import java.util.*

open class PaperEntity(
    open val native: org.bukkit.entity.Entity
) : Entity {
    override val id: UUID get() = native.uniqueId
    override val name: Component get() = native.name()
    override val location: Location get() = PaperLocation(native.location)
}
