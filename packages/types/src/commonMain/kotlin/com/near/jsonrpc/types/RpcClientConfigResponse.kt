package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class RpcClientConfigResponse(
    @SerialName("archive")
    val archive: Boolean,
    @SerialName("block_fetch_horizon")
    val blockFetchHorizon: Long,
    @SerialName("block_header_fetch_horizon")
    val blockHeaderFetchHorizon: Long,
    @SerialName("block_production_tracking_delay")
    val blockProductionTrackingDelay: List<Long>,
    @SerialName("catchup_step_period")
    val catchupStepPeriod: List<Long>,
    @SerialName("chain_id")
    val chainId: String,
    @SerialName("chunk_distribution_network")
    @Contextual
    val chunkDistributionNetwork: @Contextual Any? = null,
    @SerialName("chunk_request_retry_period")
    val chunkRequestRetryPeriod: List<Long>,
    @SerialName("chunk_validation_threads")
    val chunkValidationThreads: Long,
    @SerialName("chunk_wait_mult")
    val chunkWaitMult: List<Long>,
    @SerialName("client_background_migration_threads")
    val clientBackgroundMigrationThreads: Long,
    @SerialName("cloud_archival_reader")
    @Contextual
    val cloudArchivalReader: @Contextual Any? = null,
    @SerialName("cloud_archival_writer")
    @Contextual
    val cloudArchivalWriter: @Contextual Any? = null,
    @SerialName("doomslug_step_period")
    val doomslugStepPeriod: List<Long>,
    @SerialName("enable_multiline_logging")
    val enableMultilineLogging: Boolean,
    @SerialName("enable_statistics_export")
    val enableStatisticsExport: Boolean,
    @SerialName("epoch_length")
    val epochLength: Long,
    @SerialName("epoch_sync")
    @Contextual
    val epochSync: @Contextual Any,
    @SerialName("expected_shutdown")
    @Contextual
    val expectedShutdown: @Contextual Any,
    @SerialName("gc")
    @Contextual
    val gc: @Contextual Any,
    @SerialName("header_sync_expected_height_per_second")
    val headerSyncExpectedHeightPerSecond: Long,
    @SerialName("header_sync_initial_timeout")
    val headerSyncInitialTimeout: List<Long>,
    @SerialName("header_sync_progress_timeout")
    val headerSyncProgressTimeout: List<Long>,
    @SerialName("header_sync_stall_ban_timeout")
    val headerSyncStallBanTimeout: List<Long>,
    @SerialName("log_summary_period")
    val logSummaryPeriod: List<Long>,
    @SerialName("log_summary_style")
    @Contextual
    val logSummaryStyle: @Contextual Any,
    @SerialName("max_block_production_delay")
    val maxBlockProductionDelay: List<Long>,
    @SerialName("max_block_wait_delay")
    val maxBlockWaitDelay: List<Long>,
    @SerialName("max_gas_burnt_view")
    @Contextual
    val maxGasBurntView: @Contextual Any? = null,
    @SerialName("min_block_production_delay")
    val minBlockProductionDelay: List<Long>,
    @SerialName("min_num_peers")
    val minNumPeers: Long,
    @SerialName("num_block_producer_seats")
    val numBlockProducerSeats: Long,
    @SerialName("orphan_state_witness_max_size")
    val orphanStateWitnessMaxSize: Long,
    @SerialName("orphan_state_witness_pool_size")
    val orphanStateWitnessPoolSize: Long,
    @SerialName("produce_chunk_add_transactions_time_limit")
    val produceChunkAddTransactionsTimeLimit: String,
    @SerialName("produce_empty_blocks")
    val produceEmptyBlocks: Boolean,
    @SerialName("protocol_version_check")
    @Contextual
    val protocolVersionCheck: @Contextual Any,
    @SerialName("resharding_config")
    val reshardingConfig: String,
    @SerialName("rpc_addr")
    val rpcAddr: String? = null,
    @SerialName("save_invalid_witnesses")
    val saveInvalidWitnesses: Boolean,
    @SerialName("save_latest_witnesses")
    val saveLatestWitnesses: Boolean,
    @SerialName("save_trie_changes")
    val saveTrieChanges: Boolean,
    @SerialName("save_tx_outcomes")
    val saveTxOutcomes: Boolean,
    @SerialName("skip_sync_wait")
    val skipSyncWait: Boolean,
    @SerialName("state_request_server_threads")
    val stateRequestServerThreads: Long,
    @SerialName("state_request_throttle_period")
    val stateRequestThrottlePeriod: List<Long>,
    @SerialName("state_requests_per_throttle_period")
    val stateRequestsPerThrottlePeriod: Long,
    @SerialName("state_sync")
    @Contextual
    val stateSync: @Contextual Any,
    @SerialName("state_sync_enabled")
    val stateSyncEnabled: Boolean,
    @SerialName("state_sync_external_backoff")
    val stateSyncExternalBackoff: List<Long>,
    @SerialName("state_sync_external_timeout")
    val stateSyncExternalTimeout: List<Long>,
    @SerialName("state_sync_p2p_timeout")
    val stateSyncP2pTimeout: List<Long>,
    @SerialName("state_sync_retry_backoff")
    val stateSyncRetryBackoff: List<Long>,
    @SerialName("sync_check_period")
    val syncCheckPeriod: List<Long>,
    @SerialName("sync_height_threshold")
    val syncHeightThreshold: Long,
    @SerialName("sync_max_block_requests")
    val syncMaxBlockRequests: Long,
    @SerialName("sync_step_period")
    val syncStepPeriod: List<Long>,
    @SerialName("tracked_shards_config")
    @Contextual
    val trackedShardsConfig: @Contextual Any,
    @SerialName("transaction_pool_size_limit")
    val transactionPoolSizeLimit: Long? = null,
    @SerialName("transaction_request_handler_threads")
    val transactionRequestHandlerThreads: Long,
    @SerialName("trie_viewer_state_size_limit")
    val trieViewerStateSizeLimit: Long? = null,
    @SerialName("ttl_account_id_router")
    val ttlAccountIdRouter: List<Long>,
    @SerialName("tx_routing_height_horizon")
    val txRoutingHeightHorizon: Long,
    @SerialName("version")
    @Contextual
    val version: @Contextual Any,
    @SerialName("view_client_threads")
    val viewClientThreads: Long
)
