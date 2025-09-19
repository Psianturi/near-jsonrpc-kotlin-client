package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class ExecutionOutcomeWithIdView(
    @SerialName("block_hash")
    val blockHash: String,
    @SerialName("id")
    val id: String,
    @SerialName("outcome")
    @Contextual
    val outcome: @Contextual Any,
    @SerialName("proof")
    val proof: List<@Contextual Any>
)
