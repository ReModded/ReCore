package dev.remodded.recore.paper.data.additional

import dev.remodded.recore.api.ReCore
import dev.remodded.recore.api.data.additional.AdditionalData
import dev.remodded.recore.api.data.additional.AdditionalDataHolder
import dev.remodded.recore.api.plugins.ReCorePlugin
import dev.remodded.recore.common.data.additional.CommonAdditionalData
import dev.remodded.recore.common.data.additional.CommonAdditionalDataManager
import dev.remodded.recore.paper.data.tag.toDataTag
import dev.remodded.recore.paper.data.tag.toTag
import dev.remodded.recore.paper.entity.PaperEntity
import dev.remodded.recore.paper.entity.native
import net.minecraft.nbt.CompoundTag
import org.bukkit.craftbukkit.persistence.CraftPersistentDataContainer

class PaperAdditionalDataManager : CommonAdditionalDataManager() {

    override fun getAdditionalDataPlugins(holder: AdditionalDataHolder): Collection<ReCorePlugin> {
        val container = getAdditionalDataContainer(holder)
        return container.allKeys.mapNotNull { ReCore.INSTANCE.pluginsManager.getPlugin(it) }
    }

    override fun load(holder: AdditionalDataHolder, plugin: ReCorePlugin): AdditionalData {
        val container = getAdditionalDataContainer(holder)
        val data = CommonAdditionalData(plugin, holder)

        data.putAll(container.getCompound(plugin.getPluginInfo().id).toDataTag())

        return data
    }

    override fun save(data: AdditionalData) {
        if (!data.isDirty) return

        val data = data as CommonAdditionalData
        val container = getAdditionalDataContainer(data.dataHolder)
        val pluginTag = data.plugin.getPluginInfo().id

        if (container.contains(pluginTag))
            container.getCompound(pluginTag).merge(data.toTag())
        else
            container.put(pluginTag, data.toTag())

        data.isDirty = false
    }

    companion object {
        private const val ADDITIONAL_DATA_KEY = "recore:additional_data"

        private fun getAdditionalDataContainer(holder: AdditionalDataHolder): CompoundTag {
            val container: CraftPersistentDataContainer = when (holder) {
                is PaperEntity -> (holder.native().persistentDataContainer as CraftPersistentDataContainer)

                else -> throw IllegalArgumentException("Unsupported AdditionalDataHolder type: ${holder::class.simpleName}")
            }

            return container.getTag(ADDITIONAL_DATA_KEY) as CompoundTag? ?: CompoundTag().also { container.put(ADDITIONAL_DATA_KEY, it) }
        }

    }
}
