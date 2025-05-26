package org.benford.util

fun getFirstDigit(number: Long): Int {
    require(number > 0) { "getFirstDigit() requires a positive, non-zero number" }
    var n = number
    while (n >= 10) n /= 10
    return n.toInt()
}

fun countFirstDigits(numbers: List<Long>): Map<Int, Long> {
    val counts = numbers.map { getFirstDigit(it) }
        .groupingBy { it }
        .eachCount()
        .mapValues { it.value.toLong() }

    return (1..9).associateWith { digit ->
        counts.getOrDefault(digit, 0L)
    }
}

fun getNumberDistribution(numbers: List<Long>): Map<Int, Double> {
    val total = numbers.size.toDouble()
    val counts = numbers.map { getFirstDigit(it) }
        .groupingBy { it }
        .eachCount()

    return (1..9).associateWith { digit ->
        counts.getOrDefault(digit, 0).toDouble() / total
    }
}