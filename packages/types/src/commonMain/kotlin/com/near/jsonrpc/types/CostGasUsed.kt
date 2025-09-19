package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class CostGasUsed(
    @SerialName("cost")
    val cost: String,
    @SerialName("cost_category")
    val costCategory: String,
    @SerialName("gas_used")
    val gasUsed: String
)
