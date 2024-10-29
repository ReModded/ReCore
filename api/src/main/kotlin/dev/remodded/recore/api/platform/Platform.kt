package dev.remodded.recore.api.platform

enum class Platform(val isProxy: Boolean) {
    None(false),

    PAPER(false),
    SPONGE_API12(false),

    VELOCITY(true),
}
