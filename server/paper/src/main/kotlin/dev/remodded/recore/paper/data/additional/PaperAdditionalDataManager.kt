package dev.remodded.recore.paper.data.additional

import dev.remodded.recore.api.ReCore
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

    override fun create(holder: AdditionalDataHolder, plugin: ReCorePlugin) =
        CommonAdditionalData(plugin, holder)

    override fun load(holder: AdditionalDataHolder) {
        val container = getAdditionalDataContainer(holder)
        holder.clearAdditionalData()
        for (key in container.allKeys) {
            val plugin = ReCore.INSTANCE.pluginsManager.getPlugin(key) ?: continue
            holder.getAdditionalData(plugin).putAll(container.getCompound(key).toDataTag())
        }
    }

    override fun save(holder: AdditionalDataHolder, plugin: ReCorePlugin) {
        val container = getAdditionalDataContainer(holder)
        val data = holder.getAdditionalData(plugin) as CommonAdditionalData
        container.merge(data.toTag())
        data.isDirty = false
    }

    companion object {
        private const val ADDITIONAL_DATA_KEY = "recore:additional_data"

        private fun getAdditionalDataContainer(holder: AdditionalDataHolder): CompoundTag {
            val container: CraftPersistentDataContainer = when (holder) {
                is PaperEntity -> (holder.native().persistentDataContainer as CraftPersistentDataContainer)

                else -> throw IllegalArgumentException("Unsupported AdditionalDataHolder type: ${holder::class.simpleName}")
            }

            val tag = container.getTag(ADDITIONAL_DATA_KEY)
            return if (tag == null)
                CompoundTag().also { container.put(ADDITIONAL_DATA_KEY, it) }
            else
                tag as CompoundTag
        }

    }
}
