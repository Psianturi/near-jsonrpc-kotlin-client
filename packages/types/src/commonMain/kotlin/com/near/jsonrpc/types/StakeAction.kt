package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class StakeAction(
    @SerialName("public_key")
    @Contextual
    val publicKey: @Contextual Any,
    @SerialName("stake")
    val stake: String
)
