package org.benford.service

import org.benford.domain.BenfordDistribution
import org.benford.domain.scale
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class ChiSquareTest {
    @ParameterizedTest(name = "{index} => observed={1}, expected={0}")
    @MethodSource("provideDistributions")
    @DisplayName("ChiSquareTester returns correct result")
    fun testChiSquareTest(
        inputSampleSizes: LongArray,
        significanceLevel: Double,
        expectedPassed: Boolean
    ) {
        val totalCount = inputSampleSizes.sum()
        val expectedSizes = BenfordDistribution.distribution.scale(totalCount)
        val result = ChiSquare.test(expectedSizes, inputSampleSizes, significanceLevel)

        assertEquals(expectedPassed, result.passed)

    }

    @Test
    fun `throws when expected and observed lengths differ`() {
        val expected = doubleArrayOf(30.0, 20.0, 10.0) // only 3 values
        val observed = longArrayOf(30, 20, 10, 5)      // 4 values

        val exception = assertThrows<IllegalArgumentException> {
            ChiSquare.test(expected, observed, significanceLevel = 0.05)
        }

        assertEquals("Expected and observed arrays must be the same length.", exception.message)
    }

    companion object {
        @JvmStatic
        fun provideDistributions(): Stream<Arguments> = Stream.of(
            // Perfect match with Benford's distribution (will pass)
            Arguments.of(
                longArrayOf(30, 18, 13, 10, 8, 7, 6, 5, 4),
                0.05,
                true
            ),
            // Slightly off distribution (should still pass)
            Arguments.of(
                longArrayOf(28, 20, 10, 11, 9, 5, 6, 6, 5),
                0.05,
                true
            ),
            // Badly skewed distribution (will fail)
            Arguments.of(
                longArrayOf(5, 5, 5, 5, 5, 5, 5, 5, 60),
                0.05,
                false
            )
        )
    }
}