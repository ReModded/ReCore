package dev.remodded.recore.api.platform

import java.nio.file.Path

data class PlatformInfo(
    val platform: Platform,
    val platformName: String,
    val platformVersion: String,
    val mcVersion: String,
    val dataFolder: Path,
)
