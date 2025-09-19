package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class GCConfig(
    @SerialName("gc_blocks_limit")
    val gcBlocksLimit: Long? = null,
    @SerialName("gc_fork_clean_step")
    val gcForkCleanStep: Long? = null,
    @SerialName("gc_num_epochs_to_keep")
    val gcNumEpochsToKeep: Long? = null,
    @SerialName("gc_step_period")
    @Contextual
    val gcStepPeriod: @Contextual Any? = null
)
