package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class AccessKeyList(
    @SerialName("keys")
    val keys: List<@Contextual Any>
)
