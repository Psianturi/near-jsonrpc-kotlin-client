package com.near.jsonrpc

import com.near.jsonrpc.types.*
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Basic contract validation tests for the NEAR RPC client.
 * Tests verify transport layer works correctly with mocked responses.
 */
class ContractValidationTest {
    
    private val json = Json { 
        ignoreUnknownKeys = true 
        encodeDefaults = true
    }

    @Test
    fun `validate RPC transport handles successful response`() = runTest {
        val mockResponse = """
        {
            "jsonrpc": "2.0",
            "result": "test_result",
            "id": "kotlin-client-1"
        }
        """.trimIndent()

        val mockEngine = MockEngine { _ ->
            respond(
                content = ByteReadChannel(mockResponse),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(json)
            }
        }

        val transport = JsonRpcTransport(client, "https://rpc.testnet.near.org", json)
        val result = transport.call<kotlinx.serialization.json.JsonObject, kotlinx.serialization.json.JsonElement>(
            "test_method",
            kotlinx.serialization.json.buildJsonObject {}
        )
        
        assertNotNull(result)
    }

    @Test
    fun `validate error response handling`() = runTest {
        val mockErrorResponse = """
        {
            "jsonrpc": "2.0",
            "error": {
                "code": -32600,
                "message": "Invalid Request",
                "data": "test error data"
            },
            "id": "kotlin-client-1"
        }
        """.trimIndent()

        val mockEngine = MockEngine { _ ->
            respond(
                content = ByteReadChannel(mockErrorResponse),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(json)
            }
        }

        val transport = JsonRpcTransport(client, "https://rpc.testnet.near.org", json)

        try {
            transport.call<kotlinx.serialization.json.JsonObject, kotlinx.serialization.json.JsonElement>(
                "test_method",
                kotlinx.serialization.json.buildJsonObject {}
            )
            throw AssertionError("Should have thrown NearRpcException")
        } catch (e: com.near.jsonrpc.client.NearRpcException) {
            assertEquals("JSON-RPC error: Invalid Request", e.message)
            assertEquals(-32600, e.error.code)
            assertEquals("Invalid Request", e.error.message)
        }
    }

    @Test
    fun `validate HTTP POST method is used`() = runTest {
        val mockResponse = """{"jsonrpc":"2.0","result":"ok","id":"kotlin-client-1"}"""
        
        var requestMethodVerified = false

        val mockEngine = MockEngine { request ->
            // Verify HTTP method is POST
            requestMethodVerified = request.method.value == "POST"
            
            respond(
                content = ByteReadChannel(mockResponse),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(json)
            }
        }

        val transport = JsonRpcTransport(client, "https://rpc.testnet.near.org", json)
        transport.call<kotlinx.serialization.json.JsonObject, kotlinx.serialization.json.JsonElement>(
            "test_method",
            kotlinx.serialization.json.buildJsonObject {}
        )

        assertTrue(requestMethodVerified, "Expected POST method")
    }

    @Test
    fun `validate all typed response classes exist`() {
        // Compile-time test: ensures all typed response types are generated
        val types = listOf(
            RpcStatusResponse::class,
            RpcValidatorResponse::class,
            RpcGasPriceResponse::class,
            RpcBlockResponse::class,
            RpcChunkResponse::class,
            RpcQueryResponse::class,
            RpcTransactionResponse::class,
            RpcProtocolConfigResponse::class,
            RpcClientConfigResponse::class,
            RpcNetworkInfoResponse::class,
            RpcReceiptResponse::class
        )
        
        // Test passes if all types exist and compile
        assertTrue(types.isNotEmpty())
        assertTrue(types.size >= 11)
    }
}