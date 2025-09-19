package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class Fee(
    @SerialName("execution")
    @Contextual
    val execution: @Contextual Any,
    @SerialName("send_not_sir")
    @Contextual
    val sendNotSir: @Contextual Any,
    @SerialName("send_sir")
    @Contextual
    val sendSir: @Contextual Any
)
