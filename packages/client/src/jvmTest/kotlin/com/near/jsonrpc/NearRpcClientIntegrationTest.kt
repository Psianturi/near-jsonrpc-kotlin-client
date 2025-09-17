package com.near.jsonrpc

import com.near.jsonrpc.client.NearRpcClient
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class NearRpcClientIntegrationTest {

    private val transport = JsonRpcTransport(
        client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                })
            }
        },
        rpcUrl = "https://rpc.testnet.near.org"
    )

    private val client = NearRpcClient(transport)

    @Test
    fun testStatusCall() = runBlocking {
        println("Testing status() call to NEAR testnet...")

        val result = client.status()
        println("Status result: $result")

        assertNotNull(result)
        assertTrue(result.chainId.isNotEmpty(), "Chain ID should not be empty")
        assertTrue(result.syncInfo.latestBlockHeight > 0, "Latest block height should be greater than 0")
    }

    @Test
    fun testBlockCall() = runBlocking {
        println("Testing block() call to NEAR testnet...")

        // Create a BlockReference for the latest final block
        val blockRef = mapOf("finality" to "final")
        val result = client.block(blockRef)
        println("Block result: $result")

        assertNotNull(result)
        assertNotNull(result.header, "Block header should not be null")
        assertTrue(result.header.hash.isNotEmpty(), "Block hash should not be empty")
    }

    @Test
    fun testGasPriceCall() = runBlocking {
        println("Testing gasPrice() call to NEAR testnet...")

        // Create gas price request for latest block using Map
        val gasPriceRequest = mapOf("block_id" to null)
        val result = client.gasPrice(gasPriceRequest)
        println("Gas price result: $result")

        assertNotNull(result)
        assertTrue(result.gasPrice > 0, "Gas price should be greater than 0")
    }

    @Test
    fun testValidatorsCall() = runBlocking {
        println("Testing validators() call to NEAR testnet...")

        val result = client.validators()
        println("Validators result: $result")

        assertNotNull(result)
        assertTrue(result.currentValidators.isNotEmpty(), "Should have current validators")
    }
}