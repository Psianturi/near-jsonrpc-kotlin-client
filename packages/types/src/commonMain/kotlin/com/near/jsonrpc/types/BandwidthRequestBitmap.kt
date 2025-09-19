package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class BandwidthRequestBitmap(
    @SerialName("data")
    val data: List<Long>
)
