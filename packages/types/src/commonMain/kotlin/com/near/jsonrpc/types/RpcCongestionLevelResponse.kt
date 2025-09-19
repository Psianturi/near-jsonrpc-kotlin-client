package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class RpcCongestionLevelResponse(
    @SerialName("congestion_level")
    val congestionLevel: Double
)
