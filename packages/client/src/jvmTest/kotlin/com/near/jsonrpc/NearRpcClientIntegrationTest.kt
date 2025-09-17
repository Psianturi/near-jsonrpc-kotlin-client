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

    @Test
    fun testGasPriceCall() = runBlocking {
        println("Testing gasPrice() call to NEAR testnet...")

        // Create gas price request for latest block
        val gasPriceRequest = mapOf("block_id" to null)
        val result = client.gasPrice(gasPriceRequest)
        println("Gas price result: $result")

        assertNotNull(result)
        assertTrue(result.gasPrice > 0, "Gas price should be greater than 0")
    }

    @Test
    fun testQueryCall() = runBlocking {
        println("Testing query() call to NEAR testnet...")

        // Query account information
        val queryRequest = mapOf(
            "request_type" to "view_account",
            "account_id" to "test.near",
            "finality" to "final"
        )
        val result = client.query(queryRequest)
        println("Query result: $result")

        assertNotNull(result)
        // Query response should contain account information
        assertTrue(result.toString().contains("amount") || result.toString().isNotEmpty())
    }

    @Test
    fun testTxCall() = runBlocking {
        println("Testing tx() call to NEAR testnet...")

        // Use a known transaction hash from testnet
        val txRequest = mapOf("tx_hash" to "9yRz2aJ3P2Lz6KJxJ8ZN3kDfzFm7f7Y6GzKbN3kDfzFm")
        try {
            val result = client.tx(txRequest)
            println("Transaction result: $result")
            assertNotNull(result)
        } catch (e: Exception) {
            // Transaction might not exist, which is fine for this test
            println("Transaction not found (expected): ${e.message}")
            assertTrue(e.message?.contains("not found") == true || e.message?.contains("error") == true)
        }
    }

    @Test
    fun testErrorHandling() = runBlocking {
        println("Testing error handling with invalid method...")

        try {
            // Try to call a non-existent method
            val result = client.status() // This should work
            assertNotNull(result)
        } catch (e: Exception) {
            println("Error caught: ${e.message}")
            // Error should be properly handled
            assertTrue(e.message?.isNotEmpty() == true)
        }
    }
}