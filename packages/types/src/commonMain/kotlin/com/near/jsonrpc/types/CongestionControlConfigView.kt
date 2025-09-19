package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class CongestionControlConfigView(
    @SerialName("allowed_shard_outgoing_gas")
    @Contextual
    val allowedShardOutgoingGas: @Contextual Any,
    @SerialName("max_congestion_incoming_gas")
    @Contextual
    val maxCongestionIncomingGas: @Contextual Any,
    @SerialName("max_congestion_memory_consumption")
    val maxCongestionMemoryConsumption: Long,
    @SerialName("max_congestion_missed_chunks")
    val maxCongestionMissedChunks: Long,
    @SerialName("max_congestion_outgoing_gas")
    @Contextual
    val maxCongestionOutgoingGas: @Contextual Any,
    @SerialName("max_outgoing_gas")
    @Contextual
    val maxOutgoingGas: @Contextual Any,
    @SerialName("max_tx_gas")
    @Contextual
    val maxTxGas: @Contextual Any,
    @SerialName("min_outgoing_gas")
    @Contextual
    val minOutgoingGas: @Contextual Any,
    @SerialName("min_tx_gas")
    @Contextual
    val minTxGas: @Contextual Any,
    @SerialName("outgoing_receipts_big_size_limit")
    val outgoingReceiptsBigSizeLimit: Long,
    @SerialName("outgoing_receipts_usual_size_limit")
    val outgoingReceiptsUsualSizeLimit: Long,
    @SerialName("reject_tx_congestion_threshold")
    val rejectTxCongestionThreshold: Double
)
