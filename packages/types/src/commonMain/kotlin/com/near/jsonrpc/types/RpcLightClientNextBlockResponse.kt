package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class RpcLightClientNextBlockResponse(
    @SerialName("approvals_after_next")
    val approvalsAfterNext: List<@Contextual Any>? = null,
    @SerialName("inner_lite")
    @Contextual
    val innerLite: @Contextual Any? = null,
    @SerialName("inner_rest_hash")
    val innerRestHash: String? = null,
    @SerialName("next_block_inner_hash")
    val nextBlockInnerHash: String? = null,
    @SerialName("next_bps")
    val nextBps: List<@Contextual Any>? = null,
    @SerialName("prev_block_hash")
    val prevBlockHash: String? = null
)
