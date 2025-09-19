package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class AccessKey(
    @SerialName("nonce")
    val nonce: Long,
    @SerialName("permission")
    @Contextual
    val permission: @Contextual Any
)
