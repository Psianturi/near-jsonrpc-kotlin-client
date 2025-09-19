package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class ShardUId(
    @SerialName("shard_id")
    val shardId: Long,
    @SerialName("version")
    val version: Long
)
