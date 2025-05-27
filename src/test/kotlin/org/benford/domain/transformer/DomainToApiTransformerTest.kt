package org.benford.domain.transformer

import org.benford.common.transformer.toApi
import org.benford.domain.Output
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DomainToApiTransformerTest {

    @Test
    fun `toApi maps all fields correctly`() {
        val output = Output(
            actualDistribution = mapOf(1 to 30L, 2 to 20L),
            expectedDistribution = mapOf(1 to 29L, 2 to 21L),
            passed = true,
            pValue = 0.08,
            chiSquareValue = 4.32,
            significanceLevel = 0.05
        )

        val apiResponse = output.toApi()

        assertEquals(output.actualDistribution, apiResponse.actualDistribution)
        assertEquals(output.expectedDistribution, apiResponse.expectedDistribution)
        assertEquals(output.passed, apiResponse.passed)
        assertEquals(output.pValue, apiResponse.pValue)
        assertEquals(output.chiSquareValue, apiResponse.chiSquareValue)
        assertEquals(output.significanceLevel, apiResponse.significanceLevel)
    }
}