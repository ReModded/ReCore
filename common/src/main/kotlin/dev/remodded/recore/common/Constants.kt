package dev.remodded.recore.common

/**
 * The Constants class houses constant configuration fields that are used across the application.
 * The placeholder values found in these fields are replaced during compilation time with real values.
 *
 * @property ID The plugin identifier.
 * @property NAME The plugin name.
 * @property VERSION Plugin version.
 * @property DESCRIPTION Short plugin description.
 * @property AUTHOR Plugin authors.
 * @property URL Url for plugin home page.
 */
object Constants {
    const val ID = "@id@"
    const val NAME = "@name@"
    const val VERSION = "@version@"
    const val DESCRIPTION = "@description@"
    const val AUTHOR = "@author@"
    const val URL = "@url@"
}
