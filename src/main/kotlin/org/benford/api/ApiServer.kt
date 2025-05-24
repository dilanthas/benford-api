package org.benford.api

import io.ktor.http.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.withTimeout
import org.benford.common.Service
import org.benford.config.RestApiConfig
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.milliseconds

class ApiServer(private val config: RestApiConfig) : Service {

    private val app = embeddedServer(
        factory = Netty,
        configure = {
            shareWorkGroup = true
            connector { port = config.port }
        }
    ) {
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
            call.respondText("Hello world", status = HttpStatusCode.OK)
        }
    }

    override suspend fun start() {
        app.start(true)
    }

    override suspend fun stop() {
        app.stop(5, 10, TimeUnit.MILLISECONDS)
    }
}