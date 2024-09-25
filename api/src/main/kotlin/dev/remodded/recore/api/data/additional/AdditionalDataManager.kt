package dev.remodded.recore.api.data.additional

import dev.remodded.recore.api.plugins.ReCorePlugin

interface AdditionalDataManager {
    fun create(holder: AdditionalDataHolder, plugin: ReCorePlugin): AdditionalData

    fun load(holder: AdditionalDataHolder)

    fun save(holder: AdditionalDataHolder)
    fun save(holder: AdditionalDataHolder, plugin: ReCorePlugin)
}
