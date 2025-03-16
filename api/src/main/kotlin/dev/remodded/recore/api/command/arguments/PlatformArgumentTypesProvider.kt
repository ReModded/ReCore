package dev.remodded.recore.api.command.arguments

import dev.remodded.recore.api.ReCore
import dev.remodded.recore.api.service.getLazyService

interface PlatformArgumentTypesProvider {
    companion object : PlatformArgumentTypesProvider {
        private val provider: PlatformArgumentTypesProvider by ReCore.INSTANCE.serviceProvider.getLazyService()

        override fun player(allowMultiple: Boolean) = provider.player(allowMultiple)

        override fun resourceKey() = provider.resourceKey()
    }

    fun player(allowMultiple: Boolean): PlayerArgumentType

    fun resourceKey(): ResourceKeyArgumentType
}
