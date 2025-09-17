package com.near.jsonrpc

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlin.test.*
import kotlinx.coroutines.test.runTest

@Serializable
data class DummyParams(val foo: String)

@Serializable
data class DummyResult(val bar: String)

class JsonRpcTransportTest {

    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun testSuccessfulCall() = runTest {
        // Mock response JSON
        val mockResponse = """
            {
              "jsonrpc": "2.0",
              "result": { "bar": "baz" },
              "id": "1"
            }
        """.trimIndent()

        // Ktor mock engine
        val mockEngine = MockEngine { request ->
            assertEquals("https://rpc.testnet.near.org", request.url.toString())
            respond(
                content = mockResponse,
                status = HttpStatusCode.OK,
                headers = headersOf("Content-Type" to listOf("application/json"))
            )
        }

        val client = HttpClient(mockEngine)
        val transport = JsonRpcTransport(client, "https://rpc.testnet.near.org", json)

        val result: DummyResult = transport.call(
            method = "dummy_method",
            params = DummyParams("hello")
        )

        assertEquals("baz", result.bar)
    }

    @Test
    fun testErrorResponse() = runTest {
        val mockResponse = """
            {
              "jsonrpc": "2.0",
              "error": { "code": -32601, "message": "Method not found" },
              "id": "1"
            }
        """.trimIndent()

        val mockEngine = MockEngine { respond(mockResponse, HttpStatusCode.OK) }
        val client = HttpClient(mockEngine)
        val transport = JsonRpcTransport(client, "https://rpc.testnet.near.org", json)

        val ex = assertFailsWith<RuntimeException> {
            transport.call<DummyParams, DummyResult>(
                method = "unknown_method",
                params = DummyParams("oops")
            )
        }

        assertTrue(ex.message!!.contains("JSON-RPC error"))
    }
}