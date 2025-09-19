package com.near.jsonrpc.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

@Serializable
data class Version(
    @SerialName("build")
    val build: String,
    @SerialName("commit")
    val commit: String,
    @SerialName("rustc_version")
    val rustcVersion: String? = null,
    @SerialName("version")
    val version: String
)
