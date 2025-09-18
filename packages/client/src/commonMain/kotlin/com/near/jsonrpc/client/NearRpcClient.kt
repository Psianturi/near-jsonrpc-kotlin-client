package com.near.jsonrpc.client

import com.near.jsonrpc.JsonRpcTransport
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.buildJsonObject

/**
 * A Kotlin Multiplatform JSON-RPC client for the NEAR Protocol.
 *
 * @property transport The JsonRpcTransport used for making requests.
 */
class NearRpcClient(private val transport: JsonRpcTransport) {

    // ===== WORKING METHODS (JsonElement for compatibility) =====

    /**
     * Returns block details for the latest final block
     */
    suspend fun block(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("block", buildJsonObject {})
    }

    /**
     * Returns gas price for the most recent block
     */
    suspend fun gasPrice(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("gas_price", buildJsonObject {})
    }

    /**
     * Requests the status of the connected RPC node
     */
    suspend fun status(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("status", buildJsonObject {})
    }

    /**
     * Queries active validators on the network
     */
    suspend fun validators(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("validators", buildJsonObject {})
    }

    /**
     * Queries client node configuration
     */
    suspend fun clientConfig(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("client_config", buildJsonObject {})
    }

    /**
     * Get initial state and parameters for the genesis block
     */
    suspend fun genesisConfig(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("genesis_config", buildJsonObject {})
    }

    /**
     * Queries the current state of node network connections
     */
    suspend fun networkInfo(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("network_info", buildJsonObject {})
    }

    /**
     * Returns details of a specific chunk
     */
    suspend fun chunk(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("chunk", buildJsonObject {})
    }

    /**
     * Returns changes for a given account, contract or contract code
     */
    suspend fun changes(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("changes", buildJsonObject {})
    }

    /**
     * Returns changes in block for given block height or hash
     */
    suspend fun blockEffects(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("block_effects", buildJsonObject {})
    }

    // ===== LEGACY JSONELEMENT METHODS =====
    // For backward compatibility and methods that don't have specific types yet

    /**
     * Returns the current health status of the RPC node
     */
    suspend fun health(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("health", buildJsonObject {})
    }

    /**
     * Returns the proofs for a transaction execution
     */
    suspend fun lightClientProof(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("light_client_proof", buildJsonObject {})
    }

    /**
     * Returns the future windows for maintenance in current epoch
     */
    suspend fun maintenanceWindows(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("maintenance_windows", buildJsonObject {})
    }

    /**
     * Returns the next light client block
     */
    suspend fun nextLightClientBlock(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("next_light_client_block", buildJsonObject {})
    }

    /**
     * Sends transaction
     */
    suspend fun sendTx(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("send_tx", buildJsonObject {})
    }
}