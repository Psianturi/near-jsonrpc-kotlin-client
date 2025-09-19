package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class Tier1ProxyView(
    @SerialName("addr")
    val addr: String,
    @SerialName("peer_id")
    val peerId: String
)
