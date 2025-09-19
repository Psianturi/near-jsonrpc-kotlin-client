package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class RpcPeerInfo(
    @SerialName("account_id")
    @Contextual
    val accountId: @Contextual Any? = null,
    @SerialName("addr")
    val addr: String? = null,
    @SerialName("id")
    @Contextual
    val id: @Contextual Any
)
