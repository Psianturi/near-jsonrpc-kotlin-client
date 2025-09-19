package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class FinalExecutionOutcomeView(
    @SerialName("receipts_outcome")
    val receiptsOutcome: List<@Contextual Any>,
    @SerialName("status")
    @Contextual
    val status: @Contextual Any,
    @SerialName("transaction")
    @Contextual
    val transaction: @Contextual Any,
    @SerialName("transaction_outcome")
    @Contextual
    val transactionOutcome: @Contextual Any
)
