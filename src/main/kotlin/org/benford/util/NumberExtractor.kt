package org.benford.util

object NumberExtractor {

    // Matches numbers with optional currency symbols, minus signs, commas, and decimals
    private val numberPattern = Regex(
        """(?<!\d)([$€£¥]?-?\d{1,3}(,\d{3})*(\.\d+)?|[$€£¥]?-?\d+(\.\d+)?)(?!\d)"""
    )

    fun extractNumbers(text: String): List<Long> {
        return numberPattern.findAll(text)
            .mapNotNull { matchResult ->
                val rawNumber = matchResult.value
                val digitsOnly = removeFormatting(rawNumber)

                if (!isValidNumber(digitsOnly)) return@mapNotNull null
                digitsOnly.toLong()
            }
            .toList()
    }

    private fun removeFormatting(number: String): String {
        return number
            .replace(Regex("[\$€£¥,-]"), "")
            .replace(".", "")
    }

    private fun isValidNumber(number: String): Boolean {
        return number.isNotEmpty() &&
                number.all { it.isDigit() } &&
                number.first() != '0'  // ignore numbers starting with zero
    }
}
