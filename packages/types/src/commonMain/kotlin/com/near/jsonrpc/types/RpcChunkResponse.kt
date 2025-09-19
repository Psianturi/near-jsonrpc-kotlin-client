package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class RpcChunkResponse(
    @SerialName("author")
    val author: String,
    @SerialName("header")
    @Contextual
    val header: @Contextual Any,
    @SerialName("receipts")
    val receipts: List<@Contextual Any>,
    @SerialName("transactions")
    val transactions: List<@Contextual Any>
)
