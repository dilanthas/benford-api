package org.benford.domain

class Output(
    val expectedDistribution: Map<Int, Double>,
    val actualDistribution: Map<Int, Double>,
    val chiSquareValue: Double,
    val criticalValue: Double,
    val followsBenfordsLaw: Boolean
)