package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class GasKeyView(
    @SerialName("balance")
    val balance: Long,
    @SerialName("num_nonces")
    val numNonces: Long,
    @SerialName("permission")
    @Contextual
    val permission: @Contextual Any
)
