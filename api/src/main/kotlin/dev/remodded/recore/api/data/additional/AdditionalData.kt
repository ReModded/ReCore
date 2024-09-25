package dev.remodded.recore.api.data.additional

import dev.remodded.recore.api.data.tag.ObjectDataTag
import dev.remodded.recore.api.plugins.ReCorePlugin

interface AdditionalData : ObjectDataTag {
    val plugin: ReCorePlugin

    val isDirty: Boolean
    fun markDirty()

    fun save()
}
