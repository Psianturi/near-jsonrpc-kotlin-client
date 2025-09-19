package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class RpcProtocolConfigResponse(
    @SerialName("avg_hidden_validator_seats_per_shard")
    val avgHiddenValidatorSeatsPerShard: List<Long>,
    @SerialName("block_producer_kickout_threshold")
    val blockProducerKickoutThreshold: Long,
    @SerialName("chain_id")
    val chainId: String,
    @SerialName("chunk_producer_kickout_threshold")
    val chunkProducerKickoutThreshold: Long,
    @SerialName("chunk_validator_only_kickout_threshold")
    val chunkValidatorOnlyKickoutThreshold: Long,
    @SerialName("dynamic_resharding")
    val dynamicResharding: Boolean,
    @SerialName("epoch_length")
    val epochLength: Long,
    @SerialName("fishermen_threshold")
    val fishermenThreshold: String,
    @SerialName("gas_limit")
    @Contextual
    val gasLimit: @Contextual Any,
    @SerialName("gas_price_adjustment_rate")
    val gasPriceAdjustmentRate: List<Long>,
    @SerialName("genesis_height")
    val genesisHeight: Long,
    @SerialName("genesis_time")
    val genesisTime: String,
    @SerialName("max_gas_price")
    val maxGasPrice: String,
    @SerialName("max_inflation_rate")
    val maxInflationRate: List<Long>,
    @SerialName("max_kickout_stake_perc")
    val maxKickoutStakePerc: Long,
    @SerialName("min_gas_price")
    val minGasPrice: String,
    @SerialName("minimum_stake_divisor")
    val minimumStakeDivisor: Long,
    @SerialName("minimum_stake_ratio")
    val minimumStakeRatio: List<Long>,
    @SerialName("minimum_validators_per_shard")
    val minimumValidatorsPerShard: Long,
    @SerialName("num_block_producer_seats")
    val numBlockProducerSeats: Long,
    @SerialName("num_block_producer_seats_per_shard")
    val numBlockProducerSeatsPerShard: List<Long>,
    @SerialName("num_blocks_per_year")
    val numBlocksPerYear: Long,
    @SerialName("online_max_threshold")
    val onlineMaxThreshold: List<Long>,
    @SerialName("online_min_threshold")
    val onlineMinThreshold: List<Long>,
    @SerialName("protocol_reward_rate")
    val protocolRewardRate: List<Long>,
    @SerialName("protocol_treasury_account")
    @Contextual
    val protocolTreasuryAccount: @Contextual Any,
    @SerialName("protocol_upgrade_stake_threshold")
    val protocolUpgradeStakeThreshold: List<Long>,
    @SerialName("protocol_version")
    val protocolVersion: Long,
    @SerialName("runtime_config")
    @Contextual
    val runtimeConfig: @Contextual Any,
    @SerialName("shard_layout")
    @Contextual
    val shardLayout: @Contextual Any,
    @SerialName("shuffle_shard_assignment_for_chunk_producers")
    val shuffleShardAssignmentForChunkProducers: Boolean,
    @SerialName("target_validator_mandates_per_shard")
    val targetValidatorMandatesPerShard: Long,
    @SerialName("transaction_validity_period")
    val transactionValidityPeriod: Long
)
