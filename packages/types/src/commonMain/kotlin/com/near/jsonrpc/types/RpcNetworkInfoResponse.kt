package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class RpcNetworkInfoResponse(
    @SerialName("active_peers")
    val activePeers: List<@Contextual Any>,
    @SerialName("known_producers")
    val knownProducers: List<@Contextual Any>,
    @SerialName("num_active_peers")
    val numActivePeers: Long,
    @SerialName("peer_max_count")
    val peerMaxCount: Long,
    @SerialName("received_bytes_per_sec")
    val receivedBytesPerSec: Long,
    @SerialName("sent_bytes_per_sec")
    val sentBytesPerSec: Long
)
