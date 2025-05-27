package org.benford.service

import org.apache.commons.math3.stat.inference.ChiSquareTest
import org.benford.common.InternalServiceException
import org.benford.domain.ChiSquareResult
import org.slf4j.LoggerFactory

object ChiSquare {
    private val logger = LoggerFactory.getLogger(ChiSquare::class.java)

    fun test(
        expected: DoubleArray,
        observed: LongArray,
        significanceLevel: Double
    ): ChiSquareResult {
        require(expected.size == observed.size) {
            logger.error(
                "Chi-square input size mismatch: expected.size={}, observed.size={}",
                expected.size,
                observed.size
            )
            throw InternalServiceException(
                "Chi-square input mismatch: expected.size=${expected.size}, observed.size=${observed.size}"
            )
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