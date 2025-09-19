package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class ViewStateResult(
    @SerialName("proof")
    val proof: List<String>? = null,
    @SerialName("values")
    val values: List<@Contextual Any>
)
