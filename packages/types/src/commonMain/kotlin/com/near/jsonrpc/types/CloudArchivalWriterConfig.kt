package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class CloudArchivalWriterConfig(
    @SerialName("archive_block_data")
    val archiveBlockData: Boolean? = null,
    @SerialName("cloud_storage")
    @Contextual
    val cloudStorage: @Contextual Any,
    @SerialName("polling_interval")
    @Contextual
    val pollingInterval: @Contextual Any? = null
)
