package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class DelegateAction(
    @SerialName("actions")
    val actions: List<@Contextual Any>,
    @SerialName("max_block_height")
    val maxBlockHeight: Long,
    @SerialName("nonce")
    val nonce: Long,
    @SerialName("public_key")
    @Contextual
    val publicKey: @Contextual Any,
    @SerialName("receiver_id")
    @Contextual
    val receiverId: @Contextual Any,
    @SerialName("sender_id")
    @Contextual
    val senderId: @Contextual Any
)
