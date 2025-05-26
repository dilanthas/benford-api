package org.benford.service

import org.apache.commons.math3.distribution.ChiSquaredDistribution
import org.apache.commons.math3.stat.inference.ChiSquareTest
import org.benford.domain.ChiSquareResult

object ChiSquare {
    fun test(
        expected: DoubleArray,
        observed: LongArray,
        significanceLevel: Double
    ): ChiSquareResult {
        require(expected.size == observed.size) {
            "Expected and observed arrays must be the same length."
        }
        val chiTest = ChiSquareTest()
        val chiSquareValue = chiTest.chiSquare(expected, observed)
        val pValue = chiTest.chiSquareTest(expected, observed)
        val passed = pValue >= significanceLevel

        return ChiSquareResult(
            chiSquareValue = chiSquareValue,
            pValue = pValue,
            passed = passed
        )
    }
}