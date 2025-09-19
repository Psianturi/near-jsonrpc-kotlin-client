package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class BandwidthRequestsV1(
    @SerialName("requests")
    val requests: List<@Contextual Any>
)
