package com.near.jsonrpc

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.*
import kotlinx.serialization.*
import kotlinx.coroutines.*

/**
 * Simple transport that posts a JSON-RPC envelope to `rpcUrl` (should be the full RPC endpoint,
 * e.g. https://rpc.mainnet.near.org/  — this code will POST to that URL (server expects path '/')).
 *
 * Usage from generated client:
 *   val transport = JsonRpcTransport(httpClient, rpcUrl)
 *   val result: SomeResult = transport.call<SomeParams?, SomeResult>("method_name", params)
 */
class JsonRpcTransport(
    private val client: HttpClient,
    val rpcUrl: String,
    private val json: Json = Json { ignoreUnknownKeys = true; encodeDefaults = true }
) {

    /**
     * P = params type (may be nullable), R = return/result type
     * - Both P and R are reified so kotlinx.serialization's encode/decode helpers can work.
     */
    suspend inline fun <reified P, reified R> call(method: String, params: P? = null): R {
        // encode params to JsonElement (if any)
        val paramsElement = params?.let { json.encodeToJsonElement(it) }

        // build envelope object
        val envelope = buildJsonObject {
            put("jsonrpc", JsonPrimitive("2.0"))
            put("id", JsonPrimitive("kotlin-client-1"))
            put("method", JsonPrimitive(method))
            if (paramsElement != null) put("params", paramsElement) else put("params", JsonNull)
        }

        // POST to the rpcUrl (server expects `/` path; rpcUrl should already be the base endpoint)
        val responseText = client.post(rpcUrl) {
            contentType(ContentType.Application.Json)
            setBody(envelope.toString())
        }.bodyAsText()

        val parsed = json.parseToJsonElement(responseText).jsonObject

        parsed["error"]?.let { errorElem ->
            throw RuntimeException("JSON-RPC error: $errorElem")
        }

        val resultElem = parsed["result"] ?: throw RuntimeException("JSON-RPC response missing 'result'")

        // decode result element to R
        return json.decodeFromJsonElement(resultElem)
    }
}