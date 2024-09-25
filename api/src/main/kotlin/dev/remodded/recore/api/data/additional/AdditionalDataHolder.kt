package dev.remodded.recore.api.data.additional

import dev.remodded.recore.api.plugins.ReCorePlugin

interface AdditionalDataHolder {
    fun getAllAdditionalData(): Collection<AdditionalData>

    fun getAdditionalData(plugin: ReCorePlugin): AdditionalData
    fun removeAdditionalData(plugin: ReCorePlugin): AdditionalData?

    fun clearAdditionalData()
}
