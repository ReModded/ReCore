package dev.remodded.recore.api.utils

import kotlin.random.Random

fun <T> Collection<T>.weightedRandom(random: Random = Random, weightGetter: T.()->Int): T {
    if (this.isEmpty())
        throw IllegalStateException("Collection is empty")

    val totalWeight = sumOf { it.weightGetter() }

    if (totalWeight == 0)
        return if (this.size == 1)
            this.first()
        else
            this.random(random)

    var randomWeight = random.nextInt(totalWeight)
    for (item in this) {
        randomWeight -= item.weightGetter()
        if (randomWeight < 0)
            return item
    }

    throw IllegalStateException("UNREACHABLE: unable to select item")
}
