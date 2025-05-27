package org.benford.domain

class Output(
    val expectedDistribution: Map<Int, Long>,
    val actualDistribution: Map<Int, Long>,
    val chiSquareValue: Double,
    val pValue: Double,
    val passed: Boolean,
    val significanceLevel: Double
)