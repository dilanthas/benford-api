package org.benford.common

import org.benford.api.ApiRequest

object ApiRequestValidator {
    fun validate(request: ApiRequest,inputLimit: Long){
        if (request.input.isBlank()) {
            throw IllegalArgumentException("Input must not be blank.")
        }
        if (request.input.length > inputLimit) {
            throw IllegalArgumentException("Input exceeds maximum length of $inputLimit characters.")
        }
        if (request.significanceLevel !in 0.0..1.0) {
            throw IllegalArgumentException("Significance level must be between 0.0 and 1.0.")
        }
    }
}