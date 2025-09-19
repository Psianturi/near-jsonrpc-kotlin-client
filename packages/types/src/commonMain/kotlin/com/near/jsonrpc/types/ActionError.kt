package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class ActionError(
    @SerialName("index")
    val index: Long? = null,
    @SerialName("kind")
    @Contextual
    val kind: @Contextual Any
)
