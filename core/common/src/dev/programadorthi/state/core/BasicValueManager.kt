package dev.programadorthi.state.core

import dev.programadorthi.state.core.handler.ChangeHandler
import dev.programadorthi.state.core.handler.ErrorHandler

internal class BasicValueManager<T>(
    initialValue: T,
    private val errorHandler: ErrorHandler,
    private val changeHandler: ChangeHandler<T>,
) : BaseValueManager<T>(initialValue) {

    override fun onChanged(previous: T, next: T) {
        super.onChanged(previous, next)
        changeHandler.onChanged(previous = previous, next = next)
    }

    override fun onError(exception: Throwable) {
        super.onError(exception)
        errorHandler.onError(exception)
    }
}