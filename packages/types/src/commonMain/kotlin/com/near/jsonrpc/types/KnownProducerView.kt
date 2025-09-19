package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class KnownProducerView(
    @SerialName("account_id")
    val accountId: String,
    @SerialName("next_hops")
    val nextHops: List<String>? = null,
    @SerialName("peer_id")
    val peerId: String
)
