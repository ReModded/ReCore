package dev.remodded.recore.test.data.tag

import dev.remodded.recore.api.data.tag.DataTagProvider
import dev.remodded.recore.common.data.tag.CommonDataTagProvider

abstract class DataTagTestBase {
    companion object {
        val provider: DataTagProvider = CommonDataTagProvider
    }
}
