package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class DumpConfig(
    @SerialName("credentials_file")
    val credentialsFile: String? = null,
    @SerialName("iteration_delay")
    @Contextual
    val iterationDelay: @Contextual Any? = null,
    @SerialName("location")
    @Contextual
    val location: @Contextual Any,
    @SerialName("restart_dump_for_shards")
    val restartDumpForShards: List<Long>? = null
)
