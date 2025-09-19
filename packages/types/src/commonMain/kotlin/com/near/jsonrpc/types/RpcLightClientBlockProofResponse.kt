package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class RpcLightClientBlockProofResponse(
    @SerialName("block_header_lite")
    @Contextual
    val blockHeaderLite: @Contextual Any,
    @SerialName("block_proof")
    val blockProof: List<@Contextual Any>
)
