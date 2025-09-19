package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class StatusSyncInfo(
    @SerialName("earliest_block_hash")
    @Contextual
    val earliestBlockHash: @Contextual Any? = null,
    @SerialName("earliest_block_height")
    val earliestBlockHeight: Long? = null,
    @SerialName("earliest_block_time")
    val earliestBlockTime: String? = null,
    @SerialName("epoch_id")
    @Contextual
    val epochId: @Contextual Any? = null,
    @SerialName("epoch_start_height")
    val epochStartHeight: Long? = null,
    @SerialName("latest_block_hash")
    val latestBlockHash: String,
    @SerialName("latest_block_height")
    val latestBlockHeight: Long,
    @SerialName("latest_block_time")
    val latestBlockTime: String,
    @SerialName("latest_state_root")
    val latestStateRoot: String,
    @SerialName("syncing")
    val syncing: Boolean
)
