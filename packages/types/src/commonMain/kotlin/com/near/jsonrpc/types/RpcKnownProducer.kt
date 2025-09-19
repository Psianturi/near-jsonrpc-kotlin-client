package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class RpcKnownProducer(
    @SerialName("account_id")
    val accountId: String,
    @SerialName("addr")
    val addr: String? = null,
    @SerialName("peer_id")
    @Contextual
    val peerId: @Contextual Any
)
