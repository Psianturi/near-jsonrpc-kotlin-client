package com.near.jsonrpc.client

import com.near.jsonrpc.types.*
import com.near.jsonrpc.JsonRpcTransport
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.buildJsonObject

/**
 * A Kotlin Multiplatform JSON-RPC client for the NEAR Protocol.
 *
 * @property transport The JsonRpcTransport used for making requests.
 */
class NearRpcClient(private val transport: JsonRpcTransport) {

    /**
      * [Deprecated] Returns changes for a given account, contract or contract code for given block height or hash. Consider using changes instead.
      */
    suspend fun EXPERIMENTALChanges(params: kotlinx.serialization.json.JsonObject): RpcStateChangesInBlockByTypeResponse {
        return transport.call<kotlinx.serialization.json.JsonObject, RpcStateChangesInBlockByTypeResponse>("EXPERIMENTAL_changes", params)
    }

    /**
      * [Deprecated] Returns changes in block for given block height or hash over all transactions for all the types. Includes changes like account_touched, access_key_touched, data_touched, contract_code_touched. Consider using block_effects instead
      */
    suspend fun EXPERIMENTALChangesInBlock(params: kotlinx.serialization.json.JsonObject): RpcStateChangesInBlockResponse {
        return transport.call<kotlinx.serialization.json.JsonObject, RpcStateChangesInBlockResponse>("EXPERIMENTAL_changes_in_block", params)
    }

    /**
      * Queries the congestion level of a shard. More info about congestion [here](https://near.github.io/nearcore/architecture/how/receipt-congestion.html?highlight=congestion#receipt-congestion)
      */
    suspend fun EXPERIMENTALCongestionLevel(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("EXPERIMENTAL_congestion_level", buildJsonObject {})
    }

    /**
      * [Deprecated] Get initial state and parameters for the genesis block. Consider genesis_config instead.
      */
    suspend fun EXPERIMENTALGenesisConfig(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("EXPERIMENTAL_genesis_config", buildJsonObject {})
    }

    /**
      * Returns the proofs for a transaction execution.
      */
    suspend fun EXPERIMENTALLightClientBlockProof(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("EXPERIMENTAL_light_client_block_proof", buildJsonObject {})
    }

    /**
      * Returns the proofs for a transaction execution.
      */
    suspend fun EXPERIMENTALLightClientProof(request: RpcLightClientExecutionProofRequest): RpcLightClientExecutionProofResponse {
        return transport.call<RpcLightClientExecutionProofRequest, RpcLightClientExecutionProofResponse>("EXPERIMENTAL_light_client_proof", request)
    }

    /**
      * [Deprecated] Returns the future windows for maintenance in current epoch for the specified account. In the maintenance windows, the node will not be block producer or chunk producer. Consider using maintenance_windows instead.
      */
    suspend fun EXPERIMENTALMaintenanceWindows(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("EXPERIMENTAL_maintenance_windows", buildJsonObject {})
    }

    /**
      * A configuration that defines the protocol-level parameters such as gas/storage costs, limits, feature flags, other settings
      */
    suspend fun EXPERIMENTALProtocolConfig(params: kotlinx.serialization.json.JsonObject? = null): RpcProtocolConfigResponse {
        return transport.call<kotlinx.serialization.json.JsonObject?, RpcProtocolConfigResponse>("EXPERIMENTAL_protocol_config", params)
    }

    /**
      * Get current protocol configuration (convenience method)
      */
    suspend fun EXPERIMENTALProtocolConfig(): RpcProtocolConfigResponse {
        return EXPERIMENTALProtocolConfig(null)
    }

    /**
      * Fetches a receipt by its ID (as is, without a status or execution outcome)
      */
    suspend fun EXPERIMENTALReceipt(request: RpcReceiptRequest): RpcReceiptResponse {
        return transport.call<RpcReceiptRequest, RpcReceiptResponse>("EXPERIMENTAL_receipt", request)
    }

    /**
      * Contains the split storage information. More info on split storage [here](https://near-nodes.io/archival/split-storage-archival)
      */
    suspend fun EXPERIMENTALSplitStorageInfo(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("EXPERIMENTAL_split_storage_info", buildJsonObject {})
    }

    /**
      * Queries status of a transaction by hash, returning the final transaction result and details of all receipts.
      */
    suspend fun EXPERIMENTALTxStatus(request: RpcTransactionStatusRequest): RpcTransactionResponse {
        return transport.call<RpcTransactionStatusRequest, RpcTransactionResponse>("EXPERIMENTAL_tx_status", request)
    }

    /**
      * Returns the current epoch validators ordered in the block producer order with repetition. This endpoint is solely used for bridge currently and is not intended for other external use cases.
      */
    suspend fun EXPERIMENTALValidatorsOrdered(request: RpcValidatorsOrderedRequest? = null): RpcValidatorResponse {
        return transport.call<RpcValidatorsOrderedRequest?, RpcValidatorResponse>("EXPERIMENTAL_validators_ordered", request)
    }

    /**
      * Get current epoch validators ordered (convenience method)
      */
    suspend fun EXPERIMENTALValidatorsOrdered(): RpcValidatorResponse {
        return EXPERIMENTALValidatorsOrdered(null)
    }

    /**
      * Returns block details for given height or hash
      */
    suspend fun block(request: kotlinx.serialization.json.JsonObject): RpcBlockResponse {
        return transport.call<kotlinx.serialization.json.JsonObject, RpcBlockResponse>("block", request)
    }

    /**
      * Returns block details for given height or hash (convenience method)
      */
    suspend fun block(blockId: String): RpcBlockResponse {
        val request = kotlinx.serialization.json.buildJsonObject {
            put("block_id", kotlinx.serialization.json.JsonPrimitive(blockId))
        }
        return block(request)
    }

    /**
      * Returns block details for final block (convenience method)
      */
    suspend fun block(): RpcBlockResponse {
        val request = kotlinx.serialization.json.buildJsonObject {
            put("finality", kotlinx.serialization.json.JsonPrimitive("final"))
        }
        return block(request)
    }

    /**
      * Returns changes in block for given block height or hash over all transactions for all the types. Includes changes like account_touched, access_key_touched, data_touched, contract_code_touched.
      */
    suspend fun blockEffects(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("block_effects", buildJsonObject {})
    }

    /**
      * [Deprecated] Sends a transaction and immediately returns transaction hash. Consider using send_tx instead.
      */
    suspend fun broadcastTxAsync(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("broadcast_tx_async", buildJsonObject {})
    }

    /**
      * [Deprecated] Sends a transaction and waits until transaction is fully complete. (Has a 10 second timeout). Consider using send_tx instead.
      */
    suspend fun broadcastTxCommit(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("broadcast_tx_commit", buildJsonObject {})
    }

    /**
      * Returns changes for a given account, contract or contract code for given block height or hash.
      */
    suspend fun changes(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("changes", buildJsonObject {})
    }

    /**
      * Returns details of a specific chunk. You can run a block details query to get a valid chunk hash.
      */
    suspend fun chunk(request: kotlinx.serialization.json.JsonObject): RpcChunkResponse {
        return transport.call<kotlinx.serialization.json.JsonObject, RpcChunkResponse>("chunk", request)
    }

    /**
      * Queries client node configuration
      */
    suspend fun clientConfig(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("client_config", buildJsonObject {})
    }

    /**
      * Returns gas price for a specific block_height or block_hash. Using [null] will return the most recent block's gas price.
      */
    suspend fun gasPrice(request: RpcGasPriceRequest? = null): RpcGasPriceResponse {
        return transport.call<RpcGasPriceRequest?, RpcGasPriceResponse>("gas_price", request)
    }

    /**
      * Returns gas price for latest block (convenience method)
      */
    suspend fun gasPrice(): RpcGasPriceResponse {
        return gasPrice(null)
    }

    /**
      * Get initial state and parameters for the genesis block
      */
    suspend fun genesisConfig(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("genesis_config", buildJsonObject {})
    }

    /**
      * Returns the current health status of the RPC node the client connects to.
      */
    suspend fun health(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("health", buildJsonObject {})
    }

    /**
      * Returns the proofs for a transaction execution.
      */
    suspend fun lightClientProof(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("light_client_proof", buildJsonObject {})
    }

    /**
      * Returns the future windows for maintenance in current epoch for the specified account. In the maintenance windows, the node will not be block producer or chunk producer.
      */
    suspend fun maintenanceWindows(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("maintenance_windows", buildJsonObject {})
    }

    /**
      * Queries the current state of node network connections. This includes information about active peers, transmitted data, known producers, etc.
      */
    suspend fun networkInfo(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("network_info", buildJsonObject {})
    }

    /**
      * Returns the next light client block.
      */
    suspend fun nextLightClientBlock(request: RpcLightClientNextBlockRequest): RpcLightClientNextBlockResponse {
        return transport.call<RpcLightClientNextBlockRequest, RpcLightClientNextBlockResponse>("next_light_client_block", request)
    }

    /**
      * This module allows you to make generic requests to the network.
     *
     * The `RpcQueryRequest` struct takes in a [`BlockReference`](https://docs.rs/near-primitives/0.12.0/near_primitives/types/enum.BlockReference.html) and a [`QueryRequest`](https://docs.rs/near-primitives/0.12.0/near_primitives/views/enum.QueryRequest.html).
     *
     * The `BlockReference` enum allows you to specify a block by `Finality`, `BlockId` or `SyncCheckpoint`.
     *
     * The `QueryRequest` enum provides multiple variants for performing the following actions:
     *  - View an account's details
     *  - View a contract's code
     *  - View the state of an account
     *  - View the `AccessKey` of an account
     *  - View the `AccessKeyList` of an account
     *  - Call a function in a contract deployed on the network.
      */
    suspend fun query(request: kotlinx.serialization.json.JsonObject): RpcQueryResponse {
        return transport.call<kotlinx.serialization.json.JsonObject, RpcQueryResponse>("query", request)
    }

    /**
      * Query account details (convenience method)
      */
    suspend fun queryAccount(accountId: String, finality: String = "final"): RpcQueryResponse {
        val request = kotlinx.serialization.json.buildJsonObject {
            put("request_type", kotlinx.serialization.json.JsonPrimitive("view_account"))
            put("finality", kotlinx.serialization.json.JsonPrimitive(finality))
            put("account_id", kotlinx.serialization.json.JsonPrimitive(accountId))
        }
        return query(request)
    }

    /**
      * Query contract code (convenience method)
      */
    suspend fun queryCode(accountId: String, finality: String = "final"): RpcQueryResponse {
        val request = kotlinx.serialization.json.buildJsonObject {
            put("request_type", kotlinx.serialization.json.JsonPrimitive("view_code"))
            put("finality", kotlinx.serialization.json.JsonPrimitive(finality))
            put("account_id", kotlinx.serialization.json.JsonPrimitive(accountId))
        }
        return query(request)
    }

    /**
      * Sends transaction. Returns the guaranteed execution status and the results the blockchain can provide at the moment.
      */
    suspend fun sendTx(transaction: kotlinx.serialization.json.JsonObject): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("send_tx", transaction)
    }

    /**
      * Requests the status of the connected RPC node. This includes information about sync status, nearcore node version, protocol version, the current set of validators, etc.
      */
    suspend fun status(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("status", buildJsonObject {})
    }

    /**
      * Queries status of a transaction by hash and returns the final transaction result.
      */
    suspend fun tx(request: kotlinx.serialization.json.JsonObject): RpcTransactionResponse {
        return transport.call<kotlinx.serialization.json.JsonObject, RpcTransactionResponse>("tx", request)
    }

    /**
      * Queries active validators on the network. Returns details and the state of validation on the blockchain.
      */
    suspend fun validators(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("validators", buildJsonObject {})
    }
}
