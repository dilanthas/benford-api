package org.benford.domain

object BenfordDistribution {
    val distribution: Map<Int, Double> = mapOf(
        1 to 0.301,
        2 to 0.176,
        3 to 0.125,
        4 to 0.097,
        5 to 0.079,
        6 to 0.067,
        7 to 0.058,
        8 to 0.051,
        9 to 0.046
    )
}

fun Map<Int, Double>.scale(sampleSize: Long): DoubleArray {
    return (1..9).map { digit ->
        this.getOrDefault(digit, 0.0) * sampleSize
    }.toDoubleArray()
}