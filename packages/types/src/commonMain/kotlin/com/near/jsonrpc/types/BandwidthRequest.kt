package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class BandwidthRequest(
    @SerialName("requested_values_bitmap")
    @Contextual
    val requestedValuesBitmap: @Contextual Any,
    @SerialName("to_shard")
    val toShard: Long
)
