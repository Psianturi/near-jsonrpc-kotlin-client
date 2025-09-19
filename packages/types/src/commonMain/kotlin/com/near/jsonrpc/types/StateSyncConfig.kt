package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class StateSyncConfig(
    @SerialName("concurrency")
    @Contextual
    val concurrency: @Contextual Any? = null,
    @SerialName("dump")
    @Contextual
    val dump: @Contextual Any? = null,
    @SerialName("parts_compression_lvl")
    val partsCompressionLvl: Long? = null,
    @SerialName("sync")
    @Contextual
    val sync: @Contextual Any? = null
)
