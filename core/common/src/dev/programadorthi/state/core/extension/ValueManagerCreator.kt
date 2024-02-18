package dev.programadorthi.state.core.extension

import dev.programadorthi.state.core.BasicValueManager
import dev.programadorthi.state.core.ValueManager
import dev.programadorthi.state.core.handler.ChangeHandler
import dev.programadorthi.state.core.handler.DefaultHandler
import dev.programadorthi.state.core.handler.ErrorHandler
import dev.programadorthi.state.core.validation.Validator

public fun <T> basicValueManager(
    initialValue: T,
    validators: List<Validator<T>> = emptyList(),
    errorHandler: ErrorHandler = DefaultHandler<T>(),
    changeHandler: ChangeHandler<T> = DefaultHandler(),
): ValueManager<T> = BasicValueManager(
    initialValue = initialValue,
    errorHandler = errorHandler,
    changeHandler = changeHandler,
).also {
    it += validators
}
