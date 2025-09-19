package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class Range_of_uint64(
    @SerialName("end")
    val end: Long,
    @SerialName("start")
    val start: Long
)
