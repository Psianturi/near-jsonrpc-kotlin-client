package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class SignedTransactionView(
    @SerialName("actions")
    val actions: List<@Contextual Any>,
    @SerialName("hash")
    val hash: String,
    @SerialName("nonce")
    val nonce: Long,
    @SerialName("priority_fee")
    val priorityFee: Long? = null,
    @SerialName("public_key")
    val publicKey: String,
    @SerialName("receiver_id")
    val receiverId: String,
    @SerialName("signature")
    val signature: String,
    @SerialName("signer_id")
    val signerId: String
)
