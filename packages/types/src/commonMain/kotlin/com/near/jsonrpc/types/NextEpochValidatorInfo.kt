package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class NextEpochValidatorInfo(
    @SerialName("account_id")
    val accountId: String,
    @SerialName("public_key")
    val publicKey: String,
    @SerialName("shards")
    val shards: List<Long>,
    @SerialName("stake")
    val stake: String
)
