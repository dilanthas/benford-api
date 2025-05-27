package org.benford.common

import org.benford.api.ApiRequest

const val MAX_INPUT_LENGTH = 10_000

object ApiRequestValidator {
    fun validate(request: ApiRequest){
        if (request.input.isBlank()) {
            throw IllegalArgumentException("Input must not be blank.")
        }
        if (request.input.length > MAX_INPUT_LENGTH) {
            throw IllegalArgumentException("Input exceeds maximum length of $MAX_INPUT_LENGTH characters.")
        }
        if (request.significanceLevel !in 0.0..1.0) {
            throw IllegalArgumentException("Significance level must be between 0.0 and 1.0.")
        }
    }
}