package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class SlashedValidator(
    @SerialName("account_id")
    val accountId: String,
    @SerialName("is_double_sign")
    val isDoubleSign: Boolean
)
