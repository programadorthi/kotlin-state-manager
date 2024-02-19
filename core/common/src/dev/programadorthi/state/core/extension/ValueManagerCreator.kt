package dev.programadorthi.state.core.extension

import dev.programadorthi.state.core.BasicValueManager
import dev.programadorthi.state.core.ValueManager
import dev.programadorthi.state.core.action.ChangeAction
import dev.programadorthi.state.core.action.ErrorAction
import dev.programadorthi.state.core.validation.Validator

public fun <T> basicValueManager(
    initialValue: T,
    validators: List<Validator<T>> = emptyList(),
    changeHandler: ChangeAction<T> = { _, _ -> },
    errorHandler: ErrorAction = { },
): ValueManager<T> {
    val valueManager = BasicValueManager(initialValue = initialValue)
    valueManager += validators
    valueManager.onChanged(changeHandler)
    valueManager.onError(errorHandler)
    return valueManager
}
