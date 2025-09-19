package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class CallResult(
    @SerialName("logs")
    val logs: List<String>,
    @SerialName("result")
    val result: List<Long>
)
