package org.benford.api

import kotlinx.serialization.Serializable

@Serializable
data class ApiRequest(
    var input: String,
    val significanceLevel: Double
)
@Serializable
data class ApiResponse(
    val expectedDistribution: Map<Int, Double>,
    val actualDistribution: Map<Int, Double>,
    val chiSquareValue: Double,
    val criticalValue: Double,
    val followsBenfordsLaw: Boolean
)