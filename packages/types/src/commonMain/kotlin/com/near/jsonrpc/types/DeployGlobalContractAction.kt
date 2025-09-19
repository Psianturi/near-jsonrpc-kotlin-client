package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class DeployGlobalContractAction(
    @SerialName("code")
    val code: String,
    @SerialName("deploy_mode")
    @Contextual
    val deployMode: @Contextual Any
)
