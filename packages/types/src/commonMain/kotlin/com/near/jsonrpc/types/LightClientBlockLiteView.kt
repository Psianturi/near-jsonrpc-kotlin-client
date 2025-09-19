package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class LightClientBlockLiteView(
    @SerialName("inner_lite")
    @Contextual
    val innerLite: @Contextual Any,
    @SerialName("inner_rest_hash")
    val innerRestHash: String,
    @SerialName("prev_block_hash")
    val prevBlockHash: String
)
