package dev.remodded.recore.api.data.additional

import dev.remodded.recore.api.plugins.ReCorePlugin

interface AdditionalDataManager {
    fun load(holder: AdditionalDataHolder)
    fun load(holder: AdditionalDataHolder, plugin: ReCorePlugin): AdditionalData

    fun save(holder: AdditionalDataHolder)
    fun save(data: AdditionalData)
}
