package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class RpcStateChangesInBlockByTypeResponse(
    @SerialName("block_hash")
    val blockHash: String,
    @SerialName("changes")
    val changes: List<@Contextual Any>
)
