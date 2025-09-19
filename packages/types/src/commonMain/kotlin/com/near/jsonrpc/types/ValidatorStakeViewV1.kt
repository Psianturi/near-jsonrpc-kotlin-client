package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class ValidatorStakeViewV1(
    @SerialName("account_id")
    val accountId: String,
    @SerialName("public_key")
    val publicKey: String,
    @SerialName("stake")
    val stake: String
)
