package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class RpcLightClientNextBlockRequest(
    @SerialName("last_block_hash")
    val lastBlockHash: String
)
