package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class CloudArchivalReaderConfig(
    @SerialName("cloud_storage")
    @Contextual
    val cloudStorage: @Contextual Any
)
