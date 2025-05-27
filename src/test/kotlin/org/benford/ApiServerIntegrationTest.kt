package org.benford

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.benford.api.ApiRequest
import org.benford.api.ApiResponse
import org.benford.api.ApiServer
import org.benford.config.RestApiConfig
import org.benford.service.BenfordService
import java.net.ServerSocket
import kotlin.test.*
import io.ktor.http.*
import org.junit.jupiter.api.Test
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach


class ApiServerIntegrationTest {

    private val json = Json { ignoreUnknownKeys = true }

    private lateinit var server: ApiServer
    private lateinit var client: HttpClient
    private lateinit var baseUrl: String
    private lateinit var serverThread: Thread

    private fun findFreePort(): Int = ServerSocket(0).use { it.localPort }

    @BeforeEach
    fun setup() {
        val port = findFreePort()
        baseUrl = "http://localhost:$port/api/v1/analyze"
        server = ApiServer(RestApiConfig(port = port, requestTimeOutMillis = 5000), BenfordService())
        client = HttpClient(CIO)

        serverThread = Thread {
            runBlocking { server.start() }
        }
        serverThread.start()

        waitForServerToBeReady(baseUrl)
    }

    @AfterEach
    fun tearDown() {
        runBlocking {
            client.close()
            server.stop()
            serverThread.join()
        }
    }

    @Test
    fun `analyze endpoint returns expected Benford analysis`() = runBlocking {
        val request = ApiRequest(
            input = "123 456 789 987 654 321",
            significanceLevel = 0.05
        )

        val response = client.post(baseUrl) {
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(ApiRequest.serializer(), request))
        }

        assertEquals(HttpStatusCode.OK, response.status, "Expected 200 OK")
        val result = json.decodeFromString<ApiResponse>(response.bodyAsText())

        assertTrue(result.actualDistribution.isNotEmpty(), "Expected non-empty distribution")
        assertEquals(0.05, result.significanceLevel, "Significance level mismatch")
        assertTrue(result.pValue in 0.0..1.0, "p-value out of expected range")
    }

    @Test
    fun `returns 400 Bad Request for blank input`() = runBlocking {
        val request = ApiRequest(input = " ", significanceLevel = 0.05)

        val response = client.post(baseUrl) {
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(ApiRequest.serializer(), request))
        }

        assertEquals(HttpStatusCode.BadRequest, response.status, "Expected 400 Bad Request")
        val errorBody = response.bodyAsText()
        assertTrue(errorBody.contains("Input must not be blank"), "Error message mismatch")
    }

    private fun waitForServerToBeReady(url: String, retries: Int = 10, delayMs: Long = 300) {
        repeat(retries) {
            try {
                runBlocking {
                    client.get(url)
                }
                return
            } catch (_: Exception) {
                Thread.sleep(delayMs)
            }
        }
        error("Server did not start within expected time")
    }
}