package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class PeerInfoView(
    @SerialName("account_id")
    @Contextual
    val accountId: @Contextual Any? = null,
    @SerialName("addr")
    val addr: String,
    @SerialName("archival")
    val archival: Boolean,
    @SerialName("block_hash")
    @Contextual
    val blockHash: @Contextual Any? = null,
    @SerialName("connection_established_time_millis")
    val connectionEstablishedTimeMillis: Long,
    @SerialName("height")
    val height: Long? = null,
    @SerialName("is_highest_block_invalid")
    val isHighestBlockInvalid: Boolean,
    @SerialName("is_outbound_peer")
    val isOutboundPeer: Boolean,
    @SerialName("last_time_peer_requested_millis")
    val lastTimePeerRequestedMillis: Long,
    @SerialName("last_time_received_message_millis")
    val lastTimeReceivedMessageMillis: Long,
    @SerialName("nonce")
    val nonce: Long,
    @SerialName("peer_id")
    val peerId: String,
    @SerialName("received_bytes_per_sec")
    val receivedBytesPerSec: Long,
    @SerialName("sent_bytes_per_sec")
    val sentBytesPerSec: Long,
    @SerialName("tracked_shards")
    val trackedShards: List<Long>
)
