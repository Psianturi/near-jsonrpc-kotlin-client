package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class ChunkDistributionUris(
    @SerialName("get")
    val get: String,
    @SerialName("set")
    val set: String
)
