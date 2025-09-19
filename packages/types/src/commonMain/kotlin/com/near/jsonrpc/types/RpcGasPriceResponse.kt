package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class RpcGasPriceResponse(
    @SerialName("gas_price")
    val gasPrice: String
)
