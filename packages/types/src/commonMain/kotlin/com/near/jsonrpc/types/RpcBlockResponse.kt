package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class RpcBlockResponse(
    @SerialName("author")
    @Contextual
    val author: @Contextual Any,
    @SerialName("chunks")
    val chunks: List<@Contextual Any>,
    @SerialName("header")
    @Contextual
    val header: @Contextual Any
)
