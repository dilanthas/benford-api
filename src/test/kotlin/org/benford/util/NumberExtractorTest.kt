package org.benford.util


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NumberExtractorTest {

    @ParameterizedTest(name = "{index} => input=\"{0}\"")
    @MethodSource("inputSamples")
    @DisplayName("Extracts valid numbers from input")
    fun extractNumbers(input: String, expected: List<Long>) {
        val actual = NumberExtractor.extractNumbers(input)
        assertEquals(expected, actual)
    }

    private fun inputSamples(): Stream<Arguments> = Stream.of(
        Arguments.of("$123.45, €56.78, £0.99", listOf(12345L, 5678L)),
        Arguments.of("-$1,234.56 -€8,765.43", listOf(123456L, 876543L)),
        Arguments.of("0123 001 099 £0.99", emptyList<String>()),
        Arguments.of("100 200.50 3000", listOf(100L, 20050L, 3000L)),
        Arguments.of("0 0.50 3000", listOf(3000L)),
        Arguments.of("-100 -200.50 -3000", listOf(100L, 20050L, 3000L)),
        Arguments.of("Invoice$123.45Refund€56.78Balance9999", listOf(12345L, 5678L, 9999L)),
        Arguments.of("RefID:ABC123, Balance: 42, Total: $0.00", listOf(123L, 42L))
    )
}
