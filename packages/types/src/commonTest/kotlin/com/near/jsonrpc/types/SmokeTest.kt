package com.near.jsonrpc.types

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class SmokeTest {

    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun testRpcGasPriceResponseSerialization() {
        val jsonString = """
        {
          "gas_price": "1000000000"
        }
        """.trimIndent()

        val response = json.decodeFromString<RpcGasPriceResponse>(jsonString)
        assertNotNull(response)
        assertEquals("1000000000", response.gasPrice)
    }
}