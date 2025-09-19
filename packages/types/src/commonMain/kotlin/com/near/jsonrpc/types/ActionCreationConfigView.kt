package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class ActionCreationConfigView(
    @SerialName("add_key_cost")
    @Contextual
    val addKeyCost: @Contextual Any,
    @SerialName("create_account_cost")
    @Contextual
    val createAccountCost: @Contextual Any,
    @SerialName("delegate_cost")
    @Contextual
    val delegateCost: @Contextual Any,
    @SerialName("delete_account_cost")
    @Contextual
    val deleteAccountCost: @Contextual Any,
    @SerialName("delete_key_cost")
    @Contextual
    val deleteKeyCost: @Contextual Any,
    @SerialName("deploy_contract_cost")
    @Contextual
    val deployContractCost: @Contextual Any,
    @SerialName("deploy_contract_cost_per_byte")
    @Contextual
    val deployContractCostPerByte: @Contextual Any,
    @SerialName("function_call_cost")
    @Contextual
    val functionCallCost: @Contextual Any,
    @SerialName("function_call_cost_per_byte")
    @Contextual
    val functionCallCostPerByte: @Contextual Any,
    @SerialName("stake_cost")
    @Contextual
    val stakeCost: @Contextual Any,
    @SerialName("transfer_cost")
    @Contextual
    val transferCost: @Contextual Any
)
