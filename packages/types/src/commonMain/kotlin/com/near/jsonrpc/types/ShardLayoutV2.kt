package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class ShardLayoutV2(
    @SerialName("boundary_accounts")
    val boundaryAccounts: List<String>,
    @SerialName("id_to_index_map")
    val idToIndexMap: Map<String, Long>,
    @SerialName("index_to_id_map")
    val indexToIdMap: Map<String, Long>,
    @SerialName("shard_ids")
    val shardIds: List<Long>,
    @SerialName("shards_parent_map")
    val shardsParentMap: Map<String, Long>? = null,
    @SerialName("shards_split_map")
    val shardsSplitMap: Map<String, List<Long>>? = null,
    @SerialName("version")
    val version: Long
)
