package dev.programadorthi.state.core.validation

import androidx.compose.runtime.State

public interface ValidatorManager<T> {

    public val isValid: State<Boolean>

    public val messages: State<List<String>>

    public fun addValidator(validator: Validator<T>)

    public fun removeValidator(validator: Validator<T>)

    public fun validate(): Boolean
}