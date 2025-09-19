package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class AddKeyAction(
    @SerialName("access_key")
    @Contextual
    val accessKey: @Contextual Any,
    @SerialName("public_key")
    @Contextual
    val publicKey: @Contextual Any
)
