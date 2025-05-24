package org.benford


import org.benford.util.NumberExtractor
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
    @MethodSource("provideTextSamples")
    @DisplayName("Extracts valid numbers from text")
    fun extractNumbers(input: String, expected: List<String>) {
        val actual = NumberExtractor.extractNumbers(input)
        assertEquals(expected, actual)
    }

    fun provideTextSamples(): Stream<Arguments> = Stream.of(
        Arguments.of("$123.45, €56.78, £0.99", listOf("12345", "5678")),
        Arguments.of("-$1,234.56 -€8,765.43", listOf("123456", "876543")),
        Arguments.of("0123 001 099 £0.99", emptyList<String>()),
        Arguments.of("100 200.50 3000", listOf("100", "20050", "3000")),
        Arguments.of("Invoice$123.45Refund€56.78Balance9999", listOf("12345", "5678", "9999")),
        Arguments.of("RefID:ABC123, Balance: 42, Total: $0.00", listOf("123", "42"))
    )
}
