// util/NumberExtractor.kt
package org.benford.util

object NumberExtractor {

    // Match full number tokens with optional currency, minus, commas, and decimals
    private val regex = Regex("""(?<!\d)([\$€£¥]?-?\d{1,3}(,\d{3})*(\.\d+)?|[\$€£¥]?-?\d+(\.\d+)?)(?!\d)""")

    fun extractNumbers(text: String): List<Long> {
        return regex.findAll(text)
            .mapNotNull { match ->
                val raw = match.value

                // Remove formatting characters
                val cleaned = raw
                    .replace(Regex("[\$€£¥,-]"), "")
                    .replace(".", "")

                // Ensure the result is a valid non-zero-leading digit string
                if (cleaned.isEmpty() || !cleaned.all { it.isDigit() }) return@mapNotNull null
                if (cleaned.first() == '0') return@mapNotNull null

                cleaned.toLong()
            }
            .toList()
    }
}
