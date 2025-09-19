package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class BlockStatusView(
    @SerialName("hash")
    val hash: String,
    @SerialName("height")
    val height: Long
)
