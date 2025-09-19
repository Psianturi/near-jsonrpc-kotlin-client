package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class AccountInfo(
    @SerialName("account_id")
    val accountId: String,
    @SerialName("amount")
    val amount: String,
    @SerialName("public_key")
    val publicKey: String
)
