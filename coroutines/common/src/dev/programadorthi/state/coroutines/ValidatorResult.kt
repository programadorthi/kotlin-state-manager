package dev.programadorthi.state.coroutines

public data class ValidatorResult(
    val isValid: Boolean,
    val messages: List<String>,
)
