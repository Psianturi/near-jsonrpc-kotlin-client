package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class NetworkInfoView(
    @SerialName("connected_peers")
    val connectedPeers: List<@Contextual Any>,
    @SerialName("known_producers")
    val knownProducers: List<@Contextual Any>,
    @SerialName("num_connected_peers")
    val numConnectedPeers: Long,
    @SerialName("peer_max_count")
    val peerMaxCount: Long,
    @SerialName("tier1_accounts_data")
    val tier1AccountsData: List<@Contextual Any>,
    @SerialName("tier1_accounts_keys")
    val tier1AccountsKeys: List<String>,
    @SerialName("tier1_connections")
    val tier1Connections: List<@Contextual Any>
)
