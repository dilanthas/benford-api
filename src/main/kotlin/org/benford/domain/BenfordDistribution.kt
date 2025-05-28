package org.benford.domain

import kotlin.math.log
import kotlin.math.log10
import kotlin.math.roundToLong

object BenfordDistribution {

    private val distribution: Map<Int, Double> = (1..9).associateWith { d ->
        log10(1 + 1.0 / d)
    }

    fun expectedCounts(sampleSize: Long): DoubleArray {
        return (1..9).map { digit -> distribution.getOrDefault(digit, 0.0) * sampleSize }.toDoubleArray()
    }

    fun expectedDistribution(sampleSize: Long): Map<Int, Long> {
        return (1..9).associateWith { digit ->
            (distribution.getOrDefault(digit, 0.0) * sampleSize).roundToLong()
        }
    }
}

