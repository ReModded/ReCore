package dev.remodded.recore.api.platform

enum class Platform(val isProxy: Boolean) {
    PAPER(false),
    SPONGE_API7(false),
    SPONGE_API11(false),

    BUNGEE(true),
    VELOCITY(true),
}
