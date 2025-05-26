package org.benford.domain

data class ChiSquareResult(
    val chiSquareValue: Double,
    val passed: Boolean,
    val pValue: Double
)
