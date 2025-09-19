package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class UseGlobalContractAction(
    @SerialName("contract_identifier")
    @Contextual
    val contractIdentifier: @Contextual Any
)
