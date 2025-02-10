package dev.remodded.recore.api.world.structure

enum class StructureRotation(val degrees: Int) {
    NONE(0),

    /**
     * Rotated clockwise 90 degrees.
     */
    CLOCKWISE_90(90),

    /**
     * Rotated clockwise 180 degrees.
     */
    CLOCKWISE_180(180),

    /**
     * Rotated counterclockwise 90 degrees.
     * <br></br>
     * Equivalent to rotating clockwise 270 degrees.
     */
    COUNTERCLOCKWISE_90(270);

    companion object {
        fun fromDegrees(degrees: Int): StructureRotation {
            val degrees = degrees % 360 / 90 * 90

            return when (degrees) {
                0 -> NONE
                90 -> CLOCKWISE_90
                180 -> CLOCKWISE_180
                270 -> COUNTERCLOCKWISE_90
                else -> throw IllegalArgumentException("Invalid degrees: $degrees")
            }
        }
    }
}
