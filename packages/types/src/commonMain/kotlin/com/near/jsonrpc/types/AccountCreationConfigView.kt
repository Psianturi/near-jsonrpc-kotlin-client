package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class AccountCreationConfigView(
    @SerialName("min_allowed_top_level_account_length")
    val minAllowedTopLevelAccountLength: Long,
    @SerialName("registrar_account_id")
    @Contextual
    val registrarAccountId: @Contextual Any
)
