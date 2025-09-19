package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class LimitConfig(
    @SerialName("account_id_validity_rules_version")
    @Contextual
    val accountIdValidityRulesVersion: @Contextual Any? = null,
    @SerialName("initial_memory_pages")
    val initialMemoryPages: Long,
    @SerialName("max_actions_per_receipt")
    val maxActionsPerReceipt: Long,
    @SerialName("max_arguments_length")
    val maxArgumentsLength: Long,
    @SerialName("max_contract_size")
    val maxContractSize: Long,
    @SerialName("max_elements_per_contract_table")
    val maxElementsPerContractTable: Long? = null,
    @SerialName("max_functions_number_per_contract")
    val maxFunctionsNumberPerContract: Long? = null,
    @SerialName("max_gas_burnt")
    @Contextual
    val maxGasBurnt: @Contextual Any,
    @SerialName("max_length_method_name")
    val maxLengthMethodName: Long,
    @SerialName("max_length_returned_data")
    val maxLengthReturnedData: Long,
    @SerialName("max_length_storage_key")
    val maxLengthStorageKey: Long,
    @SerialName("max_length_storage_value")
    val maxLengthStorageValue: Long,
    @SerialName("max_locals_per_contract")
    val maxLocalsPerContract: Long? = null,
    @SerialName("max_memory_pages")
    val maxMemoryPages: Long,
    @SerialName("max_number_bytes_method_names")
    val maxNumberBytesMethodNames: Long,
    @SerialName("max_number_input_data_dependencies")
    val maxNumberInputDataDependencies: Long,
    @SerialName("max_number_logs")
    val maxNumberLogs: Long,
    @SerialName("max_number_registers")
    val maxNumberRegisters: Long,
    @SerialName("max_promises_per_function_call_action")
    val maxPromisesPerFunctionCallAction: Long,
    @SerialName("max_receipt_size")
    val maxReceiptSize: Long,
    @SerialName("max_register_size")
    val maxRegisterSize: Long,
    @SerialName("max_stack_height")
    val maxStackHeight: Long,
    @SerialName("max_tables_per_contract")
    val maxTablesPerContract: Long? = null,
    @SerialName("max_total_log_length")
    val maxTotalLogLength: Long,
    @SerialName("max_total_prepaid_gas")
    @Contextual
    val maxTotalPrepaidGas: @Contextual Any,
    @SerialName("max_transaction_size")
    val maxTransactionSize: Long,
    @SerialName("max_yield_payload_size")
    val maxYieldPayloadSize: Long,
    @SerialName("per_receipt_storage_proof_size_limit")
    val perReceiptStorageProofSizeLimit: Long,
    @SerialName("registers_memory_limit")
    val registersMemoryLimit: Long,
    @SerialName("yield_timeout_length_in_blocks")
    val yieldTimeoutLengthInBlocks: Long
)
