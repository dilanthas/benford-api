package org.benford.common.transformer

import org.benford.api.ApiResponse
import org.benford.domain.Output

fun Output.toApi() = ApiResponse(
    actualDistribution = this.actualDistribution,
    expectedDistribution = this.expectedDistribution,
    passed = this.passed,
    pValue = this.pValue,
    chiSquareValue = this.chiSquareValue,
    significanceLevel = this.significanceLevel

)