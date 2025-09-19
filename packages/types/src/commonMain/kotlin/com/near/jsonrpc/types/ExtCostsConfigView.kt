package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class ExtCostsConfigView(
    @SerialName("alt_bn128_g1_multiexp_base")
    @Contextual
    val altBn128G1MultiexpBase: @Contextual Any,
    @SerialName("alt_bn128_g1_multiexp_element")
    @Contextual
    val altBn128G1MultiexpElement: @Contextual Any,
    @SerialName("alt_bn128_g1_sum_base")
    @Contextual
    val altBn128G1SumBase: @Contextual Any,
    @SerialName("alt_bn128_g1_sum_element")
    @Contextual
    val altBn128G1SumElement: @Contextual Any,
    @SerialName("alt_bn128_pairing_check_base")
    @Contextual
    val altBn128PairingCheckBase: @Contextual Any,
    @SerialName("alt_bn128_pairing_check_element")
    @Contextual
    val altBn128PairingCheckElement: @Contextual Any,
    @SerialName("base")
    @Contextual
    val base: @Contextual Any,
    @SerialName("bls12381_g1_multiexp_base")
    val bls12381G1MultiexpBase: Long,
    @SerialName("bls12381_g1_multiexp_element")
    val bls12381G1MultiexpElement: Long,
    @SerialName("bls12381_g2_multiexp_base")
    val bls12381G2MultiexpBase: Long,
    @SerialName("bls12381_g2_multiexp_element")
    val bls12381G2MultiexpElement: Long,
    @SerialName("bls12381_map_fp2_to_g2_base")
    val bls12381MapFp2ToG2Base: Long,
    @SerialName("bls12381_map_fp2_to_g2_element")
    val bls12381MapFp2ToG2Element: Long,
    @SerialName("bls12381_map_fp_to_g1_base")
    val bls12381MapFpToG1Base: Long,
    @SerialName("bls12381_map_fp_to_g1_element")
    val bls12381MapFpToG1Element: Long,
    @SerialName("bls12381_p1_decompress_base")
    val bls12381P1DecompressBase: Long,
    @SerialName("bls12381_p1_decompress_element")
    val bls12381P1DecompressElement: Long,
    @SerialName("bls12381_p1_sum_base")
    val bls12381P1SumBase: Long,
    @SerialName("bls12381_p1_sum_element")
    val bls12381P1SumElement: Long,
    @SerialName("bls12381_p2_decompress_base")
    val bls12381P2DecompressBase: Long,
    @SerialName("bls12381_p2_decompress_element")
    val bls12381P2DecompressElement: Long,
    @SerialName("bls12381_p2_sum_base")
    val bls12381P2SumBase: Long,
    @SerialName("bls12381_p2_sum_element")
    val bls12381P2SumElement: Long,
    @SerialName("bls12381_pairing_base")
    val bls12381PairingBase: Long,
    @SerialName("bls12381_pairing_element")
    val bls12381PairingElement: Long,
    @SerialName("contract_compile_base")
    val contractCompileBase: Long,
    @SerialName("contract_compile_bytes")
    val contractCompileBytes: Long,
    @SerialName("contract_loading_base")
    @Contextual
    val contractLoadingBase: @Contextual Any,
    @SerialName("contract_loading_bytes")
    @Contextual
    val contractLoadingBytes: @Contextual Any,
    @SerialName("ecrecover_base")
    @Contextual
    val ecrecoverBase: @Contextual Any,
    @SerialName("ed25519_verify_base")
    @Contextual
    val ed25519VerifyBase: @Contextual Any,
    @SerialName("ed25519_verify_byte")
    @Contextual
    val ed25519VerifyByte: @Contextual Any,
    @SerialName("keccak256_base")
    @Contextual
    val keccak256Base: @Contextual Any,
    @SerialName("keccak256_byte")
    @Contextual
    val keccak256Byte: @Contextual Any,
    @SerialName("keccak512_base")
    @Contextual
    val keccak512Base: @Contextual Any,
    @SerialName("keccak512_byte")
    @Contextual
    val keccak512Byte: @Contextual Any,
    @SerialName("log_base")
    @Contextual
    val logBase: @Contextual Any,
    @SerialName("log_byte")
    @Contextual
    val logByte: @Contextual Any,
    @SerialName("promise_and_base")
    @Contextual
    val promiseAndBase: @Contextual Any,
    @SerialName("promise_and_per_promise")
    @Contextual
    val promiseAndPerPromise: @Contextual Any,
    @SerialName("promise_return")
    @Contextual
    val promiseReturn: @Contextual Any,
    @SerialName("read_cached_trie_node")
    @Contextual
    val readCachedTrieNode: @Contextual Any,
    @SerialName("read_memory_base")
    @Contextual
    val readMemoryBase: @Contextual Any,
    @SerialName("read_memory_byte")
    @Contextual
    val readMemoryByte: @Contextual Any,
    @SerialName("read_register_base")
    @Contextual
    val readRegisterBase: @Contextual Any,
    @SerialName("read_register_byte")
    @Contextual
    val readRegisterByte: @Contextual Any,
    @SerialName("ripemd160_base")
    @Contextual
    val ripemd160Base: @Contextual Any,
    @SerialName("ripemd160_block")
    @Contextual
    val ripemd160Block: @Contextual Any,
    @SerialName("sha256_base")
    @Contextual
    val sha256Base: @Contextual Any,
    @SerialName("sha256_byte")
    @Contextual
    val sha256Byte: @Contextual Any,
    @SerialName("storage_has_key_base")
    @Contextual
    val storageHasKeyBase: @Contextual Any,
    @SerialName("storage_has_key_byte")
    @Contextual
    val storageHasKeyByte: @Contextual Any,
    @SerialName("storage_iter_create_from_byte")
    @Contextual
    val storageIterCreateFromByte: @Contextual Any,
    @SerialName("storage_iter_create_prefix_base")
    @Contextual
    val storageIterCreatePrefixBase: @Contextual Any,
    @SerialName("storage_iter_create_prefix_byte")
    @Contextual
    val storageIterCreatePrefixByte: @Contextual Any,
    @SerialName("storage_iter_create_range_base")
    @Contextual
    val storageIterCreateRangeBase: @Contextual Any,
    @SerialName("storage_iter_create_to_byte")
    @Contextual
    val storageIterCreateToByte: @Contextual Any,
    @SerialName("storage_iter_next_base")
    @Contextual
    val storageIterNextBase: @Contextual Any,
    @SerialName("storage_iter_next_key_byte")
    @Contextual
    val storageIterNextKeyByte: @Contextual Any,
    @SerialName("storage_iter_next_value_byte")
    @Contextual
    val storageIterNextValueByte: @Contextual Any,
    @SerialName("storage_large_read_overhead_base")
    @Contextual
    val storageLargeReadOverheadBase: @Contextual Any,
    @SerialName("storage_large_read_overhead_byte")
    @Contextual
    val storageLargeReadOverheadByte: @Contextual Any,
    @SerialName("storage_read_base")
    @Contextual
    val storageReadBase: @Contextual Any,
    @SerialName("storage_read_key_byte")
    @Contextual
    val storageReadKeyByte: @Contextual Any,
    @SerialName("storage_read_value_byte")
    @Contextual
    val storageReadValueByte: @Contextual Any,
    @SerialName("storage_remove_base")
    @Contextual
    val storageRemoveBase: @Contextual Any,
    @SerialName("storage_remove_key_byte")
    @Contextual
    val storageRemoveKeyByte: @Contextual Any,
    @SerialName("storage_remove_ret_value_byte")
    @Contextual
    val storageRemoveRetValueByte: @Contextual Any,
    @SerialName("storage_write_base")
    @Contextual
    val storageWriteBase: @Contextual Any,
    @SerialName("storage_write_evicted_byte")
    @Contextual
    val storageWriteEvictedByte: @Contextual Any,
    @SerialName("storage_write_key_byte")
    @Contextual
    val storageWriteKeyByte: @Contextual Any,
    @SerialName("storage_write_value_byte")
    @Contextual
    val storageWriteValueByte: @Contextual Any,
    @SerialName("touching_trie_node")
    @Contextual
    val touchingTrieNode: @Contextual Any,
    @SerialName("utf16_decoding_base")
    @Contextual
    val utf16DecodingBase: @Contextual Any,
    @SerialName("utf16_decoding_byte")
    @Contextual
    val utf16DecodingByte: @Contextual Any,
    @SerialName("utf8_decoding_base")
    @Contextual
    val utf8DecodingBase: @Contextual Any,
    @SerialName("utf8_decoding_byte")
    @Contextual
    val utf8DecodingByte: @Contextual Any,
    @SerialName("validator_stake_base")
    @Contextual
    val validatorStakeBase: @Contextual Any,
    @SerialName("validator_total_stake_base")
    @Contextual
    val validatorTotalStakeBase: @Contextual Any,
    @SerialName("write_memory_base")
    @Contextual
    val writeMemoryBase: @Contextual Any,
    @SerialName("write_memory_byte")
    @Contextual
    val writeMemoryByte: @Contextual Any,
    @SerialName("write_register_base")
    @Contextual
    val writeRegisterBase: @Contextual Any,
    @SerialName("write_register_byte")
    @Contextual
    val writeRegisterByte: @Contextual Any,
    @SerialName("yield_create_base")
    @Contextual
    val yieldCreateBase: @Contextual Any,
    @SerialName("yield_create_byte")
    @Contextual
    val yieldCreateByte: @Contextual Any,
    @SerialName("yield_resume_base")
    @Contextual
    val yieldResumeBase: @Contextual Any,
    @SerialName("yield_resume_byte")
    @Contextual
    val yieldResumeByte: @Contextual Any
)
