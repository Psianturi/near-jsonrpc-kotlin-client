package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class ValidatorKickoutView(
    @SerialName("account_id")
    val accountId: String,
    @SerialName("reason")
    @Contextual
    val reason: @Contextual Any
)
