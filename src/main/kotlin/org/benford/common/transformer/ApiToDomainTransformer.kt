package org.benford.common.transformer

import org.benford.api.ApiRequest
import org.benford.domain.Input

fun ApiRequest.toDomain() = Input(
    input = this.input,
    significanceLevel = this.significanceLevel
)
