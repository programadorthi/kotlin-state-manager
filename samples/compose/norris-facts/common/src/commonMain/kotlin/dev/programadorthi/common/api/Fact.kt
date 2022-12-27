package dev.programadorthi.common.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Fact(
    val id: String? = null,
    val url: String? = null,
    val value: String? = null,
    @SerialName("icon_url") val iconUrl: String? = null
)