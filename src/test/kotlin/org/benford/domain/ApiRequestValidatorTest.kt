package org.benford.domain

import org.benford.api.ApiRequest
import org.benford.common.ApiRequestValidator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ApiRequestValidatorTest {
    private val inputLimit = 10_000L

    @Test
    fun `validate succeeds for valid input`() {
        val request = ApiRequest(
            input = "Some numbers like 123, 456, and 789",
            significanceLevel = 0.05
        )

        ApiRequestValidator.validate(request, inputLimit)
    }

    @Test
    fun `throws for blank input`() {
        val request = ApiRequest(
            input = "   ",
            significanceLevel = 0.05
        )

        val ex = assertThrows<IllegalArgumentException> {
            ApiRequestValidator.validate(request, inputLimit)
        }

        assertEquals("Input must not be blank.", ex.message)
    }

    @Test
    fun `throws for input longer than MAX_INPUT_LENGTH`() {
        val request = ApiRequest(
            input = "1".repeat(inputLimit.toInt() + 1),
            significanceLevel = 0.05
        )

        val ex = assertThrows<IllegalArgumentException> {
            ApiRequestValidator.validate(request, inputLimit)
        }

        assertEquals("Input exceeds maximum length of $inputLimit characters.", ex.message)
    }

    @Test
    fun `throws for significanceLevel less than 0`() {
        val request = ApiRequest(
            input = "123 456",
            significanceLevel = -0.1
        )

        val ex = assertThrows<IllegalArgumentException> {
            ApiRequestValidator.validate(request, inputLimit)
        }

        assertEquals("Significance level must be between 0.0 and 1.0.", ex.message)
    }

    @Test
    fun `throws for significanceLevel greater than 1`() {
        val request = ApiRequest(
            input = "123 456",
            significanceLevel = 1.1
        )

        val ex = assertThrows<IllegalArgumentException> {
            ApiRequestValidator.validate(request, inputLimit)
        }

        assertEquals("Significance level must be between 0.0 and 1.0.", ex.message)
    }
}