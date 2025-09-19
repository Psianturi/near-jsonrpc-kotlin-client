package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class RpcStatusResponse(
    @SerialName("chain_id")
    val chainId: String,
    @SerialName("detailed_debug_status")
    @Contextual
    val detailedDebugStatus: @Contextual Any? = null,
    @SerialName("genesis_hash")
    @Contextual
    val genesisHash: @Contextual Any,
    @SerialName("latest_protocol_version")
    val latestProtocolVersion: Long,
    @SerialName("node_key")
    @Contextual
    val nodeKey: @Contextual Any? = null,
    @SerialName("node_public_key")
    @Contextual
    val nodePublicKey: @Contextual Any,
    @SerialName("protocol_version")
    val protocolVersion: Long,
    @SerialName("rpc_addr")
    val rpcAddr: String? = null,
    @SerialName("sync_info")
    @Contextual
    val syncInfo: @Contextual Any,
    @SerialName("uptime_sec")
    val uptimeSec: Long,
    @SerialName("validator_account_id")
    @Contextual
    val validatorAccountId: @Contextual Any? = null,
    @SerialName("validator_public_key")
    @Contextual
    val validatorPublicKey: @Contextual Any? = null,
    @SerialName("validators")
    val validators: List<@Contextual Any>,
    @SerialName("version")
    @Contextual
    val version: @Contextual Any
)
