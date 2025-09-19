package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class RpcError(
    @SerialName("cause")
    @Contextual
    val cause: @Contextual Any? = null,
    @SerialName("code")
    val code: Long,
    @SerialName("data")
    @Contextual
    val data: @Contextual Any? = null,
    @SerialName("message")
    val message: String,
    @SerialName("name")
    @Contextual
    val name: @Contextual Any? = null
)
