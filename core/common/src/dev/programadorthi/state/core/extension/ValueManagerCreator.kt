package dev.programadorthi.state.core.extension

import dev.programadorthi.state.core.BaseValueManager
import dev.programadorthi.state.core.BasicValueManager
import dev.programadorthi.state.core.action.ChangeAction
import dev.programadorthi.state.core.action.ErrorAction
import dev.programadorthi.state.core.validation.Validator

public fun <T> basicValueManager(
    initialValue: T,
    validators: List<Validator<T>>? = null,
    changeHandler: ChangeAction<T>? = null,
    errorHandler: ErrorAction? = null,
): BaseValueManager<T> = BasicValueManager(initialValue = initialValue).apply {
    validators?.forEach { addValidator(it) }
    changeHandler?.let { onChanged(it) }
    errorHandler?.let { onError(it) }
}
