package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class ExecutionMetadataView(
    @SerialName("gas_profile")
    val gasProfile: List<@Contextual Any>? = null,
    @SerialName("version")
    val version: Long
)
