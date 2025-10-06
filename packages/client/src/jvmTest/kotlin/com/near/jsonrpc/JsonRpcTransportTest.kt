package com.near.jsonrpc

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import com.near.jsonrpc.client.NearRpcException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class JsonRpcTransportTest {

    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = true }

    @Test
    fun `call should decode result`() = runTest {
        val responseJson = """
        {
          "jsonrpc": "2.0",
          "id": "kotlin-client-1",
          "result": { "foo": "bar", "value": 42 }
        }
        """.trimIndent()

        val mockEngine = MockEngine { request ->
            respond(content = responseJson, status = HttpStatusCode.OK, headers = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString())))
        }

        val client = HttpClient(mockEngine)
        val transport = JsonRpcTransport(client, "https://rpc.testnet.near.org", json)

        data class Result(val foo: String, val value: Int)

        val result = transport.call<Any?, Result>("test_method", null)
        assertEquals("bar", result.foo)
        assertEquals(42, result.value)
    }

    @Test
    fun `call should throw NearRpcException on error`() = runTest {
        val responseJson = """
        {
          "jsonrpc": "2.0",
          "id": "kotlin-client-1",
          "error": { "code": -123, "message": "something went wrong", "data": null }
        }
        """.trimIndent()

        val mockEngine = MockEngine { request ->
            respond(content = responseJson, status = HttpStatusCode.OK, headers = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString())))
        }

        val client = HttpClient(mockEngine)
        val transport = JsonRpcTransport(client, "https://rpc.testnet.near.org", json)

        assertFailsWith<NearRpcException> {
            transport.call<Any?, Any>("test_method")
        }
    }

    @Test
    fun `call should throw on missing result`() = runTest {
        val responseJson = """
        {
          "jsonrpc": "2.0",
          "id": "kotlin-client-1"
        }
        """.trimIndent()

        val mockEngine = MockEngine { request ->
            respond(content = responseJson, status = HttpStatusCode.OK, headers = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString())))
        }

        val client = HttpClient(mockEngine)
        val transport = JsonRpcTransport(client, "https://rpc.testnet.near.org", json)

        assertFailsWith<RuntimeException> {
            transport.call<Any?, Any>("test_method")
        }
    }
}
