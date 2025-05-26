package org.benford.api

import customExceptionMappers
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.withTimeout
import org.benford.common.ApiRequestValidator
import org.benford.common.Service
import org.benford.common.toApi
import org.benford.common.toDomain
import org.benford.config.RestApiConfig
import org.benford.service.BenfordService
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.milliseconds

class ApiServer(private val config: RestApiConfig,private val service: BenfordService) : Service {

    private val app = embeddedServer(
        factory = Netty,
        configure = {
            shareWorkGroup = true
            connector { port = config.port }
        }
    ) {
        install(StatusPages) {
            customExceptionMappers()
        }
        install(ContentNegotiation) {
            json()
        }
        routing {
            route("/api/v1") {
                route("/analyze") {
                    analyze()
                }
            }
        }
    }

    private fun Route.analyze() = post {
        withTimeout(config.requestTimeOutMillis.milliseconds) {
            val apiRequest = call.receive<ApiRequest>()
            ApiRequestValidator.validate(apiRequest)
            call.respond(service.analyse(apiRequest.toDomain()).toApi())
        }
    }

    override suspend fun start() {
        app.start(true)
    }

    override suspend fun stop() {
        app.stop(5, 10, TimeUnit.MILLISECONDS)
    }
}