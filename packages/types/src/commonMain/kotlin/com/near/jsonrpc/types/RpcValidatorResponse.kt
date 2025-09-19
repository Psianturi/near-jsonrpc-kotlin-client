package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class RpcValidatorResponse(
    @SerialName("current_fishermen")
    val currentFishermen: List<@Contextual Any>,
    @SerialName("current_proposals")
    val currentProposals: List<@Contextual Any>,
    @SerialName("current_validators")
    val currentValidators: List<@Contextual Any>,
    @SerialName("epoch_height")
    val epochHeight: Long,
    @SerialName("epoch_start_height")
    val epochStartHeight: Long,
    @SerialName("next_fishermen")
    val nextFishermen: List<@Contextual Any>,
    @SerialName("next_validators")
    val nextValidators: List<@Contextual Any>,
    @SerialName("prev_epoch_kickout")
    val prevEpochKickout: List<@Contextual Any>
)
