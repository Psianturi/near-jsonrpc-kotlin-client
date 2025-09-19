package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class RpcTransactionResponse(
    @SerialName("final_execution_status")
    @Contextual
    val finalExecutionStatus: @Contextual Any
)
