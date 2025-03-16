package dev.remodded.recore.api.command.arguments

import dev.remodded.recore.api.ReCore
import dev.remodded.recore.api.service.getLazyService

interface PlatformArgumentTypesProvider {
    companion object : PlatformArgumentTypesProvider {
        private val provider: PlatformArgumentTypesProvider by ReCore.INSTANCE.serviceProvider.getLazyService()

        override fun player(allowMultiple: Boolean) = provider.player(allowMultiple)
    }

    fun player(allowMultiple: Boolean): PlayerArgumentType
}
