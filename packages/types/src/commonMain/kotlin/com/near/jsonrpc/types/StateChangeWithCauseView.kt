package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class StateChangeWithCauseView(
    @SerialName("cause")
    @Contextual
    val cause: @Contextual Any
)
