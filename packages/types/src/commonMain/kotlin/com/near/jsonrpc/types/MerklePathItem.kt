package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class MerklePathItem(
    @SerialName("direction")
    val direction: String,
    @SerialName("hash")
    val hash: String
)
