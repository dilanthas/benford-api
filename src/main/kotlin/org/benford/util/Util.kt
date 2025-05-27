package org.benford.util

fun getFirstDigit(number: Long): Int {
    require(number > 0) { "getFirstDigit() requires a positive, non-zero number" }
    var n = number
    while (n >= 10) n /= 10
    return n.toInt()
}

fun firstDigitDistribution(numbers: List<Long>): Map<Int, Long> {
    val rawCounts = numbers.groupingBy { getFirstDigit(it) }
        .eachCount()
        .mapValues { it.value.toLong() }

    return (1..9).associateWith { rawCounts.getOrDefault(it, 0L) }
}

fun Map<Int, Long>.toOrderedValues(): LongArray =
    (1..9).map { digit -> this.getOrDefault(digit, 0L) }.toLongArray()