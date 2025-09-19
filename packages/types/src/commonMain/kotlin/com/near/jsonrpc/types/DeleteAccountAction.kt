package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class DeleteAccountAction(
    @SerialName("beneficiary_id")
    val beneficiaryId: String
)
