package dev.remodded.recore.test.data.tag

import dev.remodded.recore.api.ReCore
import dev.remodded.recore.api.data.tag.DataTagProvider
import dev.remodded.recore.api.service.getLazyService
import dev.remodded.recore.common.data.tag.CommonDataTagProvider
import dev.remodded.recore.common.service.CommonServiceProvider
import dev.remodded.recore.test.ReCoreTest

abstract class DataTagTestBase {
    companion object {
        init {
            ReCore.INSTANCE = object : ReCoreTest() {
                override val serviceProvider = CommonServiceProvider().apply {
                    provide<DataTagProvider, CommonDataTagProvider>()
                }
            }
        }

        val provider: DataTagProvider by ReCore.INSTANCE.serviceProvider.getLazyService()
    }
}
