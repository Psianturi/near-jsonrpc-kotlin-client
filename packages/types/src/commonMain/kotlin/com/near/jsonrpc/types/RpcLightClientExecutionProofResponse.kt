package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class RpcLightClientExecutionProofResponse(
    @SerialName("block_header_lite")
    @Contextual
    val blockHeaderLite: @Contextual Any,
    @SerialName("block_proof")
    val blockProof: List<@Contextual Any>,
    @SerialName("outcome_proof")
    @Contextual
    val outcomeProof: @Contextual Any,
    @SerialName("outcome_root_proof")
    val outcomeRootProof: List<@Contextual Any>
)
