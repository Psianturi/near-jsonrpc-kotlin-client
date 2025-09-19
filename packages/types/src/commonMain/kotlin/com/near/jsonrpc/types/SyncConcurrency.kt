package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class SyncConcurrency(
    @SerialName("apply")
    val apply: Long,
    @SerialName("apply_during_catchup")
    val applyDuringCatchup: Long,
    @SerialName("peer_downloads")
    val peerDownloads: Long,
    @SerialName("per_shard")
    val perShard: Long
)
