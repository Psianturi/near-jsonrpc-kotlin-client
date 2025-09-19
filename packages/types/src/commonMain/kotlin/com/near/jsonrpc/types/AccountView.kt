package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class AccountView(
    @SerialName("amount")
    val amount: String,
    @SerialName("code_hash")
    val codeHash: String,
    @SerialName("global_contract_account_id")
    @Contextual
    val globalContractAccountId: @Contextual Any? = null,
    @SerialName("global_contract_hash")
    @Contextual
    val globalContractHash: @Contextual Any? = null,
    @SerialName("locked")
    val locked: String,
    @SerialName("storage_paid_at")
    val storagePaidAt: Long? = null,
    @SerialName("storage_usage")
    val storageUsage: Long
)
