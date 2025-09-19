package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class RpcTransactionStatusRequest(
    @SerialName("wait_until")
    @Contextual
    val waitUntil: @Contextual Any? = null
)
