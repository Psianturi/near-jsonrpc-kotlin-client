package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class ShardLayoutV1(
    @SerialName("boundary_accounts")
    val boundaryAccounts: List<String>,
    @SerialName("shards_split_map")
    val shardsSplitMap: List<List<Long>>? = null,
    @SerialName("to_parent_shard_map")
    val toParentShardMap: List<Long>? = null,
    @SerialName("version")
    val version: Long
)
