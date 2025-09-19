package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class WitnessConfigView(
    @SerialName("combined_transactions_size_limit")
    val combinedTransactionsSizeLimit: Long,
    @SerialName("main_storage_proof_size_soft_limit")
    val mainStorageProofSizeSoftLimit: Long,
    @SerialName("new_transactions_validation_state_size_soft_limit")
    val newTransactionsValidationStateSizeSoftLimit: Long
)
