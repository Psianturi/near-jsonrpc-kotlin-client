package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class ExecutionOutcomeView(
    @SerialName("executor_id")
    @Contextual
    val executorId: @Contextual Any,
    @SerialName("gas_burnt")
    @Contextual
    val gasBurnt: @Contextual Any,
    @SerialName("logs")
    val logs: List<String>,
    @SerialName("metadata")
    @Contextual
    val metadata: @Contextual Any? = null,
    @SerialName("receipt_ids")
    val receiptIds: List<String>,
    @SerialName("status")
    @Contextual
    val status: @Contextual Any,
    @SerialName("tokens_burnt")
    val tokensBurnt: String
)
