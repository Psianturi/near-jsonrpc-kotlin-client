package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class FunctionCallPermission(
    @SerialName("allowance")
    val allowance: String? = null,
    @SerialName("method_names")
    val methodNames: List<String>,
    @SerialName("receiver_id")
    val receiverId: String
)
