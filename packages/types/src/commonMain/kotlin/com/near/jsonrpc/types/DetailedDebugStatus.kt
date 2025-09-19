package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class DetailedDebugStatus(
    @SerialName("block_production_delay_millis")
    val blockProductionDelayMillis: Long,
    @SerialName("catchup_status")
    val catchupStatus: List<@Contextual Any>,
    @SerialName("current_head_status")
    @Contextual
    val currentHeadStatus: @Contextual Any,
    @SerialName("current_header_head_status")
    @Contextual
    val currentHeaderHeadStatus: @Contextual Any,
    @SerialName("network_info")
    @Contextual
    val networkInfo: @Contextual Any,
    @SerialName("sync_status")
    val syncStatus: String
)
