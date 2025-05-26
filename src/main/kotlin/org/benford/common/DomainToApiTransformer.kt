package org.benford.common

import org.benford.api.ApiResponse
import org.benford.domain.Output

fun Output.toApi() = ApiResponse(
    actualDistribution = this.actualDistribution,
    expectedDistribution = this.expectedDistribution,
    followsBenfordsLaw = this.followsBenfordsLaw,
    criticalValue = this.criticalValue,
    chiSquareValue = this.chiSquareValue
)