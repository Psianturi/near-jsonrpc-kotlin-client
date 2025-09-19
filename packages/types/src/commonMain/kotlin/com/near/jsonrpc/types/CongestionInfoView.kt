package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class CongestionInfoView(
    @SerialName("allowed_shard")
    val allowedShard: Long,
    @SerialName("buffered_receipts_gas")
    val bufferedReceiptsGas: String,
    @SerialName("delayed_receipts_gas")
    val delayedReceiptsGas: String,
    @SerialName("receipt_bytes")
    val receiptBytes: Long
)
