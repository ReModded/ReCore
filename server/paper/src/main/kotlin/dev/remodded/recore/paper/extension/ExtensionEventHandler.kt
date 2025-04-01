package dev.remodded.recore.paper.extension

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent
import dev.remodded.recore.api.ReCore
import dev.remodded.recore.api.service.getLazyService
import dev.remodded.recore.common.extention.CommonExtensionProvider
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class ExtensionEventHandler : Listener {
    private val extensionProvider: CommonExtensionProvider by ReCore.INSTANCE.serviceProvider.getLazyService()

    @EventHandler
    fun on(ev: PlayerQuitEvent) {
        extensionProvider.storages.remove(ev.player.uniqueId)
    }

    @EventHandler(priority = EventPriority.LOW)
    fun on(ev: EntityRemoveFromWorldEvent) {
        if (ev.entity !is Player && ev.entity.isDead)
            extensionProvider.storages.remove(ev.entity.uniqueId)
    }
}
