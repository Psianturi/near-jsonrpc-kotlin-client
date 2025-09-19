package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class DataReceiptCreationConfigView(
    @SerialName("base_cost")
    @Contextual
    val baseCost: @Contextual Any,
    @SerialName("cost_per_byte")
    @Contextual
    val costPerByte: @Contextual Any
)
