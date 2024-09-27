package dev.remodded.recore.common.data.additional

import dev.remodded.recore.api.data.additional.AdditionalData
import dev.remodded.recore.api.data.additional.AdditionalDataHolder
import dev.remodded.recore.api.plugins.ReCorePlugin
import dev.remodded.recore.common.data.tag.ObjectDataTag

class CommonAdditionalData(
    override val plugin: ReCorePlugin,
    val dataHolder: AdditionalDataHolder,
) : AdditionalData, dev.remodded.recore.api.data.tag.ObjectDataTag by ObjectDataTag() {

    override var isDirty: Boolean = true
    override fun markDirty() {
        isDirty = true
    }

    override fun save() {
        if (!isDirty) return
        CommonAdditionalDataManager.INSTANCE.save(this)
        isDirty = false
    }
}
