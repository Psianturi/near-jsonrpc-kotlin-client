package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class ChunkDistributionNetworkConfig(
    @SerialName("enabled")
    val enabled: Boolean,
    @SerialName("uris")
    @Contextual
    val uris: @Contextual Any
)
