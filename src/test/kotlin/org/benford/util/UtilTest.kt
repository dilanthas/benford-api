package org.benford.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class UtilTest {
    @ParameterizedTest(name = "{index} => number={0}, expectedFirstDigit={1}")
    @MethodSource("provideNumbersForFirstDigit")
    @DisplayName("getFirstDigit returns correct leading digit")
    fun testGetFirstDigit(number: Long, expected: Int) {
        val actual = getFirstDigit(number)
        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("getFirstDigit throws on 0")
    fun testGetFirstDigitThrowsOnZero() {
        val exception = assertThrows<IllegalArgumentException> {
            getFirstDigit(0)
        }
        assertEquals("getFirstDigit() requires a positive, non-zero number", exception.message)
    }

    @ParameterizedTest(name = "{index} => numbers={0}")
    @MethodSource("provideFirstDigitCounts")
    @DisplayName("getFirstDigitCounts returns correct digit counts")
    fun testGetFirstDigitCounts(numbers: List<Long>, expected: Map<Int, Long>) {
        val actual = firstDigitDistribution(numbers)
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "{index} => numbers={0}, expectedDistribution={1}")
    @MethodSource("provideNumberListsForDistribution")
    @DisplayName("getNumberDistribution computes correct digit frequency")
    fun testGetNumberDistribution(numbers: List<Long>, expected: Map<Int, Long>) {
        val actual = firstDigitDistribution(numbers)
        assertEquals(expected, actual)
    }

    companion object {
        @JvmStatic
        fun provideNumbersForFirstDigit(): Stream<Arguments> = Stream.of(
            Arguments.of(1L, 1),
            Arguments.of(9L, 9),
            Arguments.of(123L, 1),
            Arguments.of(987654321L, 9),
            Arguments.of(42L, 4),
            Arguments.of(700L, 7),
            Arguments.of(10L, 1),
            Arguments.of(1000000000L, 1)
        )

        @JvmStatic
        fun provideNumberListsForDistribution(): Stream<Arguments> = Stream.of(
            Arguments.of(
                listOf(123L, 789L, 456L),
                mapOf(
                    1 to 1L,
                    2 to 0,
                    3 to 0,
                    4 to 1L,
                    5 to 0,
                    6 to 0,
                    7 to 1L,
                    8 to 0,
                    9 to 0
                )
            ),
            Arguments.of(
                listOf(11L, 12L, 13L, 21L, 22L, 31L),
                mapOf(
                    1 to 3L,
                    2 to 2L,
                    3 to 1L,
                    4 to 0,
                    5 to 0,
                    6 to 0,
                    7 to 0,
                    8 to 0,
                    9 to 0
                )
            ),
            Arguments.of(
                listOf(100L, 101L, 102L, 200L, 300L),
                mapOf(
                    1 to 3L,
                    2 to 1L,
                    3 to 1L,
                    4 to 0,
                    5 to 0,
                    6 to 0,
                    7 to 0,
                    8 to 0,
                    9 to 0
                )
            )
        )
        @JvmStatic
        fun provideFirstDigitCounts(): Stream<Arguments> = Stream.of(
            Arguments.of(
                listOf(123L, 456L, 789L),
                mapOf(1 to 1L, 2 to 0L, 3 to 0L, 4 to 1L, 5 to 0L, 6 to 0L, 7 to 1L, 8 to 0L, 9 to 0L)
            ),
            Arguments.of(
                listOf(11L, 12L, 13L, 21L, 22L, 31L),
                mapOf(1 to 3L, 2 to 2L, 3 to 1L, 4 to 0L, 5 to 0L, 6 to 0L, 7 to 0L, 8 to 0L, 9 to 0L)
            ),
            Arguments.of(
                listOf(100L, 200L, 200L, 200L, 300L),
                mapOf(1 to 1L, 2 to 3L, 3 to 1L, 4 to 0L, 5 to 0L, 6 to 0L, 7 to 0L, 8 to 0L, 9 to 0L)
            ),
            Arguments.of(
                emptyList<Long>(),
                (1..9).associateWith { 0L }
            ),
            Arguments.of(
                listOf(9L, 99L, 900L),
                mapOf(1 to 0L, 2 to 0L, 3 to 0L, 4 to 0L, 5 to 0L, 6 to 0L, 7 to 0L, 8 to 0L, 9 to 3L)
            )
        )
    }
}