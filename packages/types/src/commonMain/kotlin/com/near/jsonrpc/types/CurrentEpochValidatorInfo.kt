package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class CurrentEpochValidatorInfo(
    @SerialName("account_id")
    val accountId: String,
    @SerialName("is_slashed")
    val isSlashed: Boolean,
    @SerialName("num_expected_blocks")
    val numExpectedBlocks: Long,
    @SerialName("num_expected_chunks")
    val numExpectedChunks: Long? = null,
    @SerialName("num_expected_chunks_per_shard")
    val numExpectedChunksPerShard: List<Long>? = null,
    @SerialName("num_expected_endorsements")
    val numExpectedEndorsements: Long? = null,
    @SerialName("num_expected_endorsements_per_shard")
    val numExpectedEndorsementsPerShard: List<Long>? = null,
    @SerialName("num_produced_blocks")
    val numProducedBlocks: Long,
    @SerialName("num_produced_chunks")
    val numProducedChunks: Long? = null,
    @SerialName("num_produced_chunks_per_shard")
    val numProducedChunksPerShard: List<Long>? = null,
    @SerialName("num_produced_endorsements")
    val numProducedEndorsements: Long? = null,
    @SerialName("num_produced_endorsements_per_shard")
    val numProducedEndorsementsPerShard: List<Long>? = null,
    @SerialName("public_key")
    val publicKey: String,
    @SerialName("shards")
    val shards: List<Long>,
    @SerialName("shards_endorsed")
    val shardsEndorsed: List<Long>? = null,
    @SerialName("stake")
    val stake: String
)
