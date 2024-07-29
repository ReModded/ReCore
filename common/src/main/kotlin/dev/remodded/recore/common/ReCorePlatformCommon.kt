package dev.remodded.recore.common

import dev.remodded.recore.api.ReCorePlatform
import org.slf4j.Logger

interface ReCorePlatformCommon : ReCorePlatform {
    override val logger: Logger
        get() = ReCoreImpl.logger
}
