package com.near.jsonrpc.types

import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Basic smoke tests for generated types.
 * Verifies that key types exist and basic serialization works.
 */
class SerializationTest {
    private val json = Json { 
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    @Test
    fun `test Version type roundtrip`() {
        val version = Version(
            version = "1.0.0",
            build = "abc123",
            commit = "def456"
        )
        
        val encoded = json.encodeToString(version)
        val decoded = json.decodeFromString<Version>(encoded)
        
        assertEquals(version.version, decoded.version)
        assertEquals(version.build, decoded.build)
        assertEquals(version.commit, decoded.commit)
    }

    @Test
    fun `test TransferAction serialization`() {
        val action = TransferAction(deposit = "1000000")
        val encoded = json.encodeToString(action)
        
        assertNotNull(encoded)
        assertTrue(encoded.contains("1000000"))
        
        val decoded = json.decodeFromString<TransferAction>(encoded)
        assertEquals("1000000", decoded.deposit)
    }

    @Test
    fun `test all key types exist and compile`() {
        // Compile-time test: verifies all main generated types exist
        val typesList = listOf(
            Version::class.simpleName,
            TransferAction::class.simpleName,
            AccountView::class.simpleName,
            RpcStatusResponse::class.simpleName,
            RpcValidatorResponse::class.simpleName,
            RpcGasPriceResponse::class.simpleName,
            RpcBlockResponse::class.simpleName,
            RpcQueryResponse::class.simpleName,
            RpcTransactionResponse::class.simpleName
        )
        
        assertTrue(typesList.all { it != null })
        assertTrue(typesList.size >= 9)
    }
}