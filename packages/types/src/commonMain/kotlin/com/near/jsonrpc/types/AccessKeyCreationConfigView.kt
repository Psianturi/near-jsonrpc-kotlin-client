package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class AccessKeyCreationConfigView(
    @SerialName("full_access_cost")
    @Contextual
    val fullAccessCost: @Contextual Any,
    @SerialName("function_call_cost")
    @Contextual
    val functionCallCost: @Contextual Any,
    @SerialName("function_call_cost_per_byte")
    @Contextual
    val functionCallCostPerByte: @Contextual Any
)
