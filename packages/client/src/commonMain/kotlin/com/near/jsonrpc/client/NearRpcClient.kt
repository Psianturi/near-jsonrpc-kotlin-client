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
    suspend fun EXPERIMENTALChanges(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("EXPERIMENTAL_changes", buildJsonObject {})
    }

    /**
      * [Deprecated] Returns changes in block for given block height or hash over all transactions for all the types. Includes changes like account_touched, access_key_touched, data_touched, contract_code_touched. Consider using block_effects instead
      */
    suspend fun EXPERIMENTALChangesInBlock(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("EXPERIMENTAL_changes_in_block", buildJsonObject {})
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
    suspend fun EXPERIMENTALLightClientProof(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("EXPERIMENTAL_light_client_proof", buildJsonObject {})
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
    suspend fun EXPERIMENTALProtocolConfig(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("EXPERIMENTAL_protocol_config", buildJsonObject {})
    }

    /**
      * Fetches a receipt by its ID (as is, without a status or execution outcome)
      */
    suspend fun EXPERIMENTALReceipt(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("EXPERIMENTAL_receipt", buildJsonObject {})
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
    suspend fun EXPERIMENTALTxStatus(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("EXPERIMENTAL_tx_status", buildJsonObject {})
    }

    /**
      * Returns the current epoch validators ordered in the block producer order with repetition. This endpoint is solely used for bridge currently and is not intended for other external use cases.
      */
    suspend fun EXPERIMENTALValidatorsOrdered(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("EXPERIMENTAL_validators_ordered", buildJsonObject {})
    }

    /**
      * Returns block details for given height or hash
      */
    suspend fun block(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("block", buildJsonObject {})
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
    suspend fun chunk(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("chunk", buildJsonObject {})
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
    suspend fun gasPrice(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("gas_price", buildJsonObject {})
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
    suspend fun nextLightClientBlock(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("next_light_client_block", buildJsonObject {})
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
    suspend fun query(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("query", buildJsonObject {})
    }

    /**
      * Sends transaction. Returns the guaranteed execution status and the results the blockchain can provide at the moment.
      */
    suspend fun sendTx(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("send_tx", buildJsonObject {})
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
    suspend fun tx(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("tx", buildJsonObject {})
    }

    /**
      * Queries active validators on the network. Returns details and the state of validation on the blockchain.
      */
    suspend fun validators(): JsonElement {
        return transport.call<kotlinx.serialization.json.JsonObject, JsonElement>("validators", buildJsonObject {})
    }
}
