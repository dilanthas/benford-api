package org.benford.api

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.benford.common.Service

class ApiServer():Service {

    private val app = embeddedServer(
        factory = Netty
    )
    override suspend fun start() {
        TODO("Not yet implemented")
    }

    override suspend fun stop() {
        TODO("Not yet implemented")
    }
}