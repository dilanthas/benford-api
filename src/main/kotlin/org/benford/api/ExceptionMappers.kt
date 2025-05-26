import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.plugins.statuspages.StatusPagesConfig
import io.ktor.server.plugins.statuspages.exception
import io.ktor.server.response.respond
import org.benford.api.ApiErrorResponse

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
}