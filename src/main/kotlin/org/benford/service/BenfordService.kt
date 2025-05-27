package org.benford.service

import org.benford.domain.BenfordDistribution
import org.benford.domain.Input
import org.benford.domain.Output
import org.benford.util.NumberExtractor
import org.benford.util.firstDigitDistribution
import org.benford.util.toOrderedValues
import org.slf4j.LoggerFactory

class BenfordService {
    private val logger = LoggerFactory.getLogger(BenfordService::class.java)

    fun analyse(input: Input): Output {
        val extractedNumbers = NumberExtractor.extractNumbers(input.input)
        if (extractedNumbers.isEmpty()) {
            logger.warn("No valid numbers found in input")
            throw IllegalArgumentException("No valid numbers found in the input")
        }
        val inputDistribution = firstDigitDistribution(extractedNumbers)
        val sampleSize = inputDistribution.values.sum()
        val expectedDigitCounts = BenfordDistribution.expectedCounts(sampleSize)
        val result =
            ChiSquare.test(expectedDigitCounts, inputDistribution.toOrderedValues(), input.significanceLevel)
        return Output(
            expectedDistribution = BenfordDistribution.expectedDistribution(sampleSize),
            passed = result.passed,
            chiSquareValue = result.chiSquareValue,
            pValue = result.pValue,
            actualDistribution = inputDistribution,
            significanceLevel = input.significanceLevel
        )
    }
}