package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class RpcSplitStorageInfoResponse(
    @SerialName("cold_head_height")
    val coldHeadHeight: Long? = null,
    @SerialName("final_head_height")
    val finalHeadHeight: Long? = null,
    @SerialName("head_height")
    val headHeight: Long? = null,
    @SerialName("hot_db_kind")
    val hotDbKind: String? = null
)
