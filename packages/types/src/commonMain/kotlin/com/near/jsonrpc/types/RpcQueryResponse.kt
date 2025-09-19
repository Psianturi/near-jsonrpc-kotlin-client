package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class RpcQueryResponse(
    @SerialName("block_hash")
    val blockHash: String,
    @SerialName("block_height")
    val blockHeight: Long
)
