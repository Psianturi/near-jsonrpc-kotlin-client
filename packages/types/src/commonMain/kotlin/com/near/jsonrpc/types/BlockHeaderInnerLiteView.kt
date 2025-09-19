package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class BlockHeaderInnerLiteView(
    @SerialName("block_merkle_root")
    @Contextual
    val blockMerkleRoot: @Contextual Any,
    @SerialName("epoch_id")
    @Contextual
    val epochId: @Contextual Any,
    @SerialName("height")
    val height: Long,
    @SerialName("next_bp_hash")
    @Contextual
    val nextBpHash: @Contextual Any,
    @SerialName("next_epoch_id")
    @Contextual
    val nextEpochId: @Contextual Any,
    @SerialName("outcome_root")
    val outcomeRoot: String,
    @SerialName("prev_state_root")
    val prevStateRoot: String,
    @SerialName("timestamp")
    val timestamp: Long,
    @SerialName("timestamp_nanosec")
    val timestampNanosec: String
)
