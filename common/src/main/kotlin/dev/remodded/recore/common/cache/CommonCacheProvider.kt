package dev.remodded.recore.common.cache

import dev.remodded.recore.api.cache.CacheProvider

abstract class CommonCacheProvider : CacheProvider {
    companion object {
        private val sanitizationPattern = "[^a-zA-Z0-9_:]".toRegex()

        fun sanitizeName(name: String) {
            if (name.contains(sanitizationPattern)) {
                throw IllegalArgumentException("Cache name can contain only alphanumeric characters, colons and underscores. Got: '$name'.")
            }
        }
    }

}
