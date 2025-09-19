package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class ShardLayoutV0(
    @SerialName("num_shards")
    val numShards: Long,
    @SerialName("version")
    val version: Long
)
