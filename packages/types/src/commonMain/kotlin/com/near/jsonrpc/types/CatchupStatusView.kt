package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class CatchupStatusView(
    @SerialName("blocks_to_catchup")
    val blocksToCatchup: List<@Contextual Any>,
    @SerialName("shard_sync_status")
    @Contextual
    val shardSyncStatus: @Contextual Any,
    @SerialName("sync_block_hash")
    val syncBlockHash: String,
    @SerialName("sync_block_height")
    val syncBlockHeight: Long
)
