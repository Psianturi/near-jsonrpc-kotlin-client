package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class RpcSendTransactionRequest(
    @SerialName("signed_tx_base64")
    val signedTxBase64: String,
    @SerialName("wait_until")
    @Contextual
    val waitUntil: @Contextual Any? = null
)
