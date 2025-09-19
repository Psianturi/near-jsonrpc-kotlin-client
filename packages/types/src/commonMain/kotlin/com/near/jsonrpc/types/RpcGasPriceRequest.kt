package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class RpcGasPriceRequest(
    @SerialName("block_id")
    @Contextual
    val blockId: @Contextual Any? = null
)
