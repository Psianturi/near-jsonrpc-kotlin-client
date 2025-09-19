package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class StateItem(
    @SerialName("key")
    val key: String,
    @SerialName("value")
    val value: String
)
