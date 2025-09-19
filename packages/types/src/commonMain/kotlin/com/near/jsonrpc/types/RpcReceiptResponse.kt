package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class RpcReceiptResponse(
    @SerialName("predecessor_id")
    val predecessorId: String,
    @SerialName("priority")
    val priority: Long? = null,
    @SerialName("receipt")
    @Contextual
    val receipt: @Contextual Any,
    @SerialName("receipt_id")
    val receiptId: String,
    @SerialName("receiver_id")
    val receiverId: String
)
