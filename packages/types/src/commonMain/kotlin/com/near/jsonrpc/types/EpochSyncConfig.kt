package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class EpochSyncConfig(
    @SerialName("disable_epoch_sync_for_bootstrapping")
    val disableEpochSyncForBootstrapping: Boolean? = null,
    @SerialName("epoch_sync_horizon")
    val epochSyncHorizon: Long,
    @SerialName("ignore_epoch_sync_network_requests")
    val ignoreEpochSyncNetworkRequests: Boolean? = null,
    @SerialName("timeout_for_epoch_sync")
    @Contextual
    val timeoutForEpochSync: @Contextual Any
)
