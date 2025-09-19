package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class MissingTrieValue(
    @SerialName("context")
    @Contextual
    val context: @Contextual Any,
    @SerialName("hash")
    val hash: String
)
