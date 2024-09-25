package dev.remodded.recore.common.data.additional

import dev.remodded.recore.api.data.additional.AdditionalData
import dev.remodded.recore.api.data.additional.AdditionalDataHolder
import dev.remodded.recore.api.plugins.ReCorePlugin

class CommonAdditionalDataHolder(val delegator: AdditionalDataHolder) : AdditionalDataHolder {
    val additionalData: MutableMap<String, AdditionalData> = mutableMapOf()

    init {
        CommonAdditionalDataManager.INSTANCE.load(delegator)
    }

    override fun getAllAdditionalData(): Collection<AdditionalData> {
        return additionalData.values
    }

    override fun getAdditionalData(plugin: ReCorePlugin): AdditionalData {
        return additionalData.getOrPut(plugin.getPluginInfo().id) {
            CommonAdditionalDataManager.INSTANCE.create(delegator, plugin)
        }
    }

    override fun removeAdditionalData(plugin: ReCorePlugin): AdditionalData? {
        return additionalData.remove(plugin.getPluginInfo().id)
    }

    override fun clearAdditionalData() {
        additionalData.clear()
    }
}
