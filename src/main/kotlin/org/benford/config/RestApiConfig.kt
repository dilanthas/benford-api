package org.benford.config

import kotlinx.serialization.Serializable

@Serializable
data class RestApiConfig(
    val port: Int,
    val requestTimeOutMillis: Long = 3_000,
    val inputLimit: Long = 10_000
)
