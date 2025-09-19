package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class RpcLightClientBlockProofRequest(
    @SerialName("block_hash")
    val blockHash: String,
    @SerialName("light_client_head")
    val lightClientHead: String
)
