import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.plugins.statuspages.StatusPagesConfig
import io.ktor.server.plugins.statuspages.exception
import io.ktor.server.response.respond
import org.benford.api.ApiErrorResponse
import org.benford.common.InternalServiceException

internal fun StatusPagesConfig.customExceptionMappers() {
    status(HttpStatusCode.NotFound) { call, _ ->
        call.respond(
            HttpStatusCode.NotFound, ApiErrorResponse(
                status = HttpStatusCode.NotFound.value,
                message = "Not Found"
            )
        )
    }
    exception<IllegalArgumentException> { call: ApplicationCall, cause: IllegalArgumentException ->
        call.respond(
            HttpStatusCode.BadRequest,
            ApiErrorResponse(
                status = HttpStatusCode.BadRequest.value,
                message = cause.message ?: "Unknown error"
            )
        )
    }
    exception<InternalServiceException> { call, _ ->
        call.respond(
            HttpStatusCode.InternalServerError,
            ApiErrorResponse(
                status = HttpStatusCode.InternalServerError.value,
                message = "An internal error occurred while processing your request."
            )
        )
    }
}