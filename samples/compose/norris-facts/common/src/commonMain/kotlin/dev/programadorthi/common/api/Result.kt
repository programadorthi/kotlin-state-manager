package dev.programadorthi.common.api

import kotlinx.serialization.Serializable

@Serializable
data class Result(
    val total: Int? = null,
    val result: List<Fact>? = null
)
