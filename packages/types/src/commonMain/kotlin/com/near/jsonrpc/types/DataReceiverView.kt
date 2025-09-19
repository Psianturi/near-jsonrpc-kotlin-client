package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class DataReceiverView(
    @SerialName("data_id")
    val dataId: String,
    @SerialName("receiver_id")
    val receiverId: String
)
