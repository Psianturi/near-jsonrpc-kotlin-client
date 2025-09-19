package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class DurationAsStdSchemaProvider(
    @SerialName("nanos")
    val nanos: Long,
    @SerialName("secs")
    val secs: Long
)
