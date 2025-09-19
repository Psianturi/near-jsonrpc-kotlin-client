package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class AccountDataView(
    @SerialName("account_key")
    @Contextual
    val accountKey: @Contextual Any,
    @SerialName("peer_id")
    @Contextual
    val peerId: @Contextual Any,
    @SerialName("proxies")
    val proxies: List<@Contextual Any>,
    @SerialName("timestamp")
    val timestamp: String
)
