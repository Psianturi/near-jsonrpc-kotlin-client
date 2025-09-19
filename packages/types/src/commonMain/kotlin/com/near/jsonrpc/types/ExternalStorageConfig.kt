package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class ExternalStorageConfig(
    @SerialName("external_storage_fallback_threshold")
    val externalStorageFallbackThreshold: Long? = null,
    @SerialName("location")
    @Contextual
    val location: @Contextual Any,
    @SerialName("num_concurrent_requests")
    val numConcurrentRequests: Long? = null,
    @SerialName("num_concurrent_requests_during_catchup")
    val numConcurrentRequestsDuringCatchup: Long? = null
)
