package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class AccessKeyInfoView(
    @SerialName("access_key")
    @Contextual
    val accessKey: @Contextual Any,
    @SerialName("public_key")
    val publicKey: String
)
