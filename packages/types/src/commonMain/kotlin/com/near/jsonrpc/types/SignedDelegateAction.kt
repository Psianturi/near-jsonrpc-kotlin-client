package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class SignedDelegateAction(
    @SerialName("delegate_action")
    @Contextual
    val delegateAction: @Contextual Any,
    @SerialName("signature")
    val signature: String
)
