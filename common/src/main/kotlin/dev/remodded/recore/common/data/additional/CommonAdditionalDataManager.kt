package dev.remodded.recore.common.data.additional

import dev.remodded.recore.api.ReCore
import dev.remodded.recore.api.data.additional.AdditionalData
import dev.remodded.recore.api.data.additional.AdditionalDataHolder
import dev.remodded.recore.api.data.additional.AdditionalDataManager
import dev.remodded.recore.api.service.getLazyService
import kotlin.getValue

abstract class CommonAdditionalDataManager : AdditionalDataManager {

    override fun save(holder: AdditionalDataHolder) {
        holder.getAllAdditionalData().forEach(AdditionalData::save)
    }

    companion object {
        val INSTANCE: AdditionalDataManager by ReCore.INSTANCE.serviceProvider.getLazyService()
    }
}
