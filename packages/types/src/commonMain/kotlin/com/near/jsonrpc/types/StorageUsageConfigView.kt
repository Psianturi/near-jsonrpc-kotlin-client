package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class StorageUsageConfigView(
    @SerialName("num_bytes_account")
    val numBytesAccount: Long,
    @SerialName("num_extra_bytes_record")
    val numExtraBytesRecord: Long
)
