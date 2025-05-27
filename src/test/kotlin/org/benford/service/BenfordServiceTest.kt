package org.benford.service

import org.benford.domain.Input
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BenfordServiceTest {

    private val service = BenfordService()

    @Test
    fun `throws exception when input has no valid numbers`() {
        val input = Input("no numbers here!", significanceLevel = 0.05)

        val exception = assertThrows<IllegalArgumentException> {
            service.analyse(input)
        }

        assertEquals("No valid numbers found in the input", exception.message)
    }

    @Test
    fun `returns expected output for numbers that loosely follow Benford's Law`() {
        val numbers = listOf("123", "234", "345", "456", "567", "678", "789", "890", "912")
        val text = numbers.joinToString(" ")
        val input = Input(text, significanceLevel = 0.05)

        val result = service.analyse(input)

        assertEquals(9, result.expectedDistribution.size)
        assertEquals(9, result.actualDistribution.size)
        assertTrue(result.pValue in 0.0..1.0)
        assertTrue(result.chiSquareValue >= 0.0)
    }

    @Test
    fun `detects non-Benford distribution with repeated 9s`() {
        val repeatedNines = List(1000) { "9999" }.joinToString(" ")
        val input = Input(repeatedNines, significanceLevel = 0.05)

        val result = service.analyse(input)

        assertFalse(result.passed)
        assertTrue(result.pValue < 0.05)
        assertEquals(1000L, result.actualDistribution.values.sum())
        assertEquals(1000L, result.expectedDistribution.values.sum())
    }

    @Test
    fun `passes test for balanced distribution resembling Benford`() {
        val inputText = buildString {
            append("123 145 167 189 ")   // 1-heavy
            append("234 245 ")           // 2
            append("321 345 ")           // 3
            append("456 467 ")           // 4
            append("579 567 ")           // 5
            append("678 ")               // 6
            append("789 ")               // 7
            append("812 ")               // 8
            append("934 945")            // 9
        }

        val input = Input(inputText, significanceLevel = 0.05)
        val result = service.analyse(input)

        assertTrue(result.passed)
        assertTrue(result.pValue >= 0.05)
    }
}