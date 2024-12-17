package dev.remodded.recore.test.data.tag

import com.google.gson.Gson
import dev.remodded.recore.api.ReCore
import dev.remodded.recore.api.data.tag.DataTagProvider
import dev.remodded.recore.api.service.getLazyService
import dev.remodded.recore.api.service.provide
import dev.remodded.recore.common.data.tag.CommonDataTagProvider
import dev.remodded.recore.common.service.CommonServiceProvider
import dev.remodded.recore.common.utils.gson.GsonProvider
import dev.remodded.recore.test.ReCoreTest

abstract class DataTagTestBase {
    companion object {
        init {
            object : ReCoreTest() {
                override val serviceProvider = CommonServiceProvider().apply {
                    provide<Gson>(ReCore.INSTANCE) { GsonProvider.GSON }
                    provide<DataTagProvider, CommonDataTagProvider>(ReCore.INSTANCE)
                }
            }
        }

        val provider: DataTagProvider by ReCore.INSTANCE.serviceProvider.getLazyService()
    }
}
