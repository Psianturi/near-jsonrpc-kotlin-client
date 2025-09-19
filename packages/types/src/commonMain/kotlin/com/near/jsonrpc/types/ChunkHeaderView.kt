package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class ChunkHeaderView(
    @SerialName("balance_burnt")
    val balanceBurnt: String,
    @SerialName("bandwidth_requests")
    @Contextual
    val bandwidthRequests: @Contextual Any? = null,
    @SerialName("chunk_hash")
    val chunkHash: String,
    @SerialName("congestion_info")
    @Contextual
    val congestionInfo: @Contextual Any? = null,
    @SerialName("encoded_length")
    val encodedLength: Long,
    @SerialName("encoded_merkle_root")
    val encodedMerkleRoot: String,
    @SerialName("gas_limit")
    val gasLimit: Long,
    @SerialName("gas_used")
    val gasUsed: Long,
    @SerialName("height_created")
    val heightCreated: Long,
    @SerialName("height_included")
    val heightIncluded: Long,
    @SerialName("outcome_root")
    val outcomeRoot: String,
    @SerialName("outgoing_receipts_root")
    val outgoingReceiptsRoot: String,
    @SerialName("prev_block_hash")
    val prevBlockHash: String,
    @SerialName("prev_state_root")
    val prevStateRoot: String,
    @SerialName("rent_paid")
    val rentPaid: String,
    @SerialName("shard_id")
    val shardId: Long,
    @SerialName("signature")
    val signature: String,
    @SerialName("tx_root")
    val txRoot: String,
    @SerialName("validator_proposals")
    val validatorProposals: List<@Contextual Any>,
    @SerialName("validator_reward")
    val validatorReward: String
)
