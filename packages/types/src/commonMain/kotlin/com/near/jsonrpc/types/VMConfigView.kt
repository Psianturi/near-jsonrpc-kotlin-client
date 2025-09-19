package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class VMConfigView(
    @SerialName("deterministic_account_ids")
    val deterministicAccountIds: Boolean,
    @SerialName("discard_custom_sections")
    val discardCustomSections: Boolean,
    @SerialName("eth_implicit_accounts")
    val ethImplicitAccounts: Boolean,
    @SerialName("ext_costs")
    @Contextual
    val extCosts: @Contextual Any,
    @SerialName("fix_contract_loading_cost")
    val fixContractLoadingCost: Boolean,
    @SerialName("global_contract_host_fns")
    val globalContractHostFns: Boolean,
    @SerialName("grow_mem_cost")
    val growMemCost: Long,
    @SerialName("implicit_account_creation")
    val implicitAccountCreation: Boolean,
    @SerialName("limit_config")
    @Contextual
    val limitConfig: @Contextual Any,
    @SerialName("reftypes_bulk_memory")
    val reftypesBulkMemory: Boolean,
    @SerialName("regular_op_cost")
    val regularOpCost: Long,
    @SerialName("saturating_float_to_int")
    val saturatingFloatToInt: Boolean,
    @SerialName("storage_get_mode")
    @Contextual
    val storageGetMode: @Contextual Any,
    @SerialName("vm_kind")
    @Contextual
    val vmKind: @Contextual Any
)
