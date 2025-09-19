package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class BlockHeaderView(
    @SerialName("approvals")
    val approvals: List<@Contextual Any>,
    @SerialName("block_body_hash")
    @Contextual
    val blockBodyHash: @Contextual Any? = null,
    @SerialName("block_merkle_root")
    val blockMerkleRoot: String,
    @SerialName("block_ordinal")
    val blockOrdinal: Long? = null,
    @SerialName("challenges_result")
    val challengesResult: List<@Contextual Any>,
    @SerialName("challenges_root")
    val challengesRoot: String,
    @SerialName("chunk_endorsements")
    val chunkEndorsements: List<List<Long>>? = null,
    @SerialName("chunk_headers_root")
    val chunkHeadersRoot: String,
    @SerialName("chunk_mask")
    val chunkMask: List<Boolean>,
    @SerialName("chunk_receipts_root")
    val chunkReceiptsRoot: String,
    @SerialName("chunk_tx_root")
    val chunkTxRoot: String,
    @SerialName("chunks_included")
    val chunksIncluded: Long,
    @SerialName("epoch_id")
    val epochId: String,
    @SerialName("epoch_sync_data_hash")
    @Contextual
    val epochSyncDataHash: @Contextual Any? = null,
    @SerialName("gas_price")
    val gasPrice: String,
    @SerialName("hash")
    val hash: String,
    @SerialName("height")
    val height: Long,
    @SerialName("last_ds_final_block")
    val lastDsFinalBlock: String,
    @SerialName("last_final_block")
    val lastFinalBlock: String,
    @SerialName("latest_protocol_version")
    val latestProtocolVersion: Long,
    @SerialName("next_bp_hash")
    val nextBpHash: String,
    @SerialName("next_epoch_id")
    val nextEpochId: String,
    @SerialName("outcome_root")
    val outcomeRoot: String,
    @SerialName("prev_hash")
    @Contextual
    val prevHash: @Contextual Any,
    @SerialName("prev_height")
    val prevHeight: Long? = null,
    @SerialName("prev_state_root")
    val prevStateRoot: String,
    @SerialName("random_value")
    val randomValue: String,
    @SerialName("rent_paid")
    val rentPaid: String,
    @SerialName("signature")
    @Contextual
    val signature: @Contextual Any,
    @SerialName("timestamp")
    val timestamp: Long,
    @SerialName("timestamp_nanosec")
    val timestampNanosec: String,
    @SerialName("total_supply")
    val totalSupply: String,
    @SerialName("validator_proposals")
    val validatorProposals: List<@Contextual Any>,
    @SerialName("validator_reward")
    val validatorReward: String
)
