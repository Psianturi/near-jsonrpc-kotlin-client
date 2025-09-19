package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class ContractCodeView(
    @SerialName("code_base64")
    val codeBase64: String,
    @SerialName("hash")
    val hash: String
)
