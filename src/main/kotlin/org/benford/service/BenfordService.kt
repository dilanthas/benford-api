package org.benford.service

import org.apache.commons.math3.distribution.ChiSquaredDistribution
import org.benford.util.getNumberDistribution
import org.benford.domain.Input
import org.benford.domain.Output
import org.benford.util.NumberExtractor

class BenfordService {

    private val expected = mapOf(
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

    fun analyse(input: Input): Output {
        val extractedNumbers = NumberExtractor.extractNumbers(input.input)
        if (extractedNumbers.isEmpty()) {
            throw IllegalArgumentException("No valid numbers found in the input")
        }
        val actualDistribution = getNumberDistribution(extractedNumbers)
        return Output(
            expectedDistribution = mapOf(),
            followsBenfordsLaw = false,
            chiSquareValue = Double.MAX_VALUE,
            criticalValue = Double.MAX_VALUE,
            actualDistribution = mapOf()
        )
    }




    private fun chiSquareTest(
        actual: Map<Int, Double>,
        sampleSize: Int,
        significanceLevel: Double
    ): Triple<Double, Double, Boolean> {
        val chiSquare = expected.map { (digit, expectedProb) ->
            val observed = actual.getOrDefault(digit, 0.0) * sampleSize
            val expectedFreq = expectedProb * sampleSize
            (observed - expectedFreq) * (observed - expectedFreq) / expectedFreq
        }.sum()

        val degreesOfFreedom = 8
        val chiDist = ChiSquaredDistribution(degreesOfFreedom.toDouble())
        val criticalValue = chiDist.inverseCumulativeProbability(1.0 - significanceLevel)
        val passed = chiSquare < criticalValue

        return Triple(chiSquare, criticalValue, passed)
    }

    private fun calculateDistribution(numbers: List<String>){

    }
}