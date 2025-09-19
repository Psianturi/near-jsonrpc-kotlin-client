package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class RuntimeFeesConfigView(
    @SerialName("action_creation_config")
    @Contextual
    val actionCreationConfig: @Contextual Any,
    @SerialName("action_receipt_creation_config")
    @Contextual
    val actionReceiptCreationConfig: @Contextual Any,
    @SerialName("burnt_gas_reward")
    val burntGasReward: List<Long>,
    @SerialName("data_receipt_creation_config")
    @Contextual
    val dataReceiptCreationConfig: @Contextual Any,
    @SerialName("pessimistic_gas_price_inflation_ratio")
    val pessimisticGasPriceInflationRatio: List<Long>,
    @SerialName("storage_usage_config")
    @Contextual
    val storageUsageConfig: @Contextual Any
)
