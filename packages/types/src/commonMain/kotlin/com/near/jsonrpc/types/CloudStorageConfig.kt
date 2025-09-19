package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class CloudStorageConfig(
    @SerialName("credentials_file")
    val credentialsFile: String? = null,
    @SerialName("storage")
    @Contextual
    val storage: @Contextual Any
)
