package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class FunctionCallAction(
    @SerialName("args")
    val args: String,
    @SerialName("deposit")
    val deposit: String,
    @SerialName("gas")
    val gas: Long,
    @SerialName("method_name")
    val methodName: String
)
