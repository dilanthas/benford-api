package org.benford.api

import kotlinx.serialization.Serializable

@Serializable
data class ApiRequest(
    var input: String,
    val significanceLevel: Double
)
@Serializable
data class ApiResponse(
    val expectedDistribution: Map<Int, Long>,
    val actualDistribution: Map<Int, Long>,
    val chiSquareValue: Double,
    val pValue: Double,
    val passed: Boolean,
    val significanceLevel: Double
)