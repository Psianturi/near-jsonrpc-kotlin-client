package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class RuntimeConfigView(
    @SerialName("account_creation_config")
    @Contextual
    val accountCreationConfig: @Contextual Any,
    @SerialName("congestion_control_config")
    @Contextual
    val congestionControlConfig: @Contextual Any,
    @SerialName("storage_amount_per_byte")
    val storageAmountPerByte: String,
    @SerialName("transaction_costs")
    @Contextual
    val transactionCosts: @Contextual Any,
    @SerialName("wasm_config")
    @Contextual
    val wasmConfig: @Contextual Any,
    @SerialName("witness_config")
    @Contextual
    val witnessConfig: @Contextual Any
)
