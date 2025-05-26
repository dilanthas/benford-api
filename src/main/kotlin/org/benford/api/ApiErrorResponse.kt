package org.benford.api

import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorResponse(
    val status: Int,
    val message: String = "Unknown error"
)
