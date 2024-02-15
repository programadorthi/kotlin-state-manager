package dev.programadorthi.state.core

import dev.programadorthi.state.core.handler.AfterChangeLifecycleHandler
import dev.programadorthi.state.core.handler.BeforeChangeLifecycleHandler
import dev.programadorthi.state.core.handler.ErrorHandler
import dev.programadorthi.state.core.handler.TransformHandler

internal class BasicValueManager<T>(
    initialValue: T,
    private val errorHandler: ErrorHandler,
    private val onAfterChange: AfterChangeLifecycleHandler<T>,
    private val onBeforeChange: BeforeChangeLifecycleHandler<T>,
    private val transformHandler: TransformHandler<T>
) : BaseValueManager<T>(initialValue) {

    override fun onAfterChange(previous: T, current: T) {
        super.onAfterChange(previous, current)
        onAfterChange.onAfterChange(previous, current)
    }

    override fun onBeforeChange(current: T, next: T) {
        super.onBeforeChange(current, next)
        onBeforeChange.onBeforeChange(current, next)
    }

    override fun onError(exception: Throwable) {
        super.onError(exception)
        errorHandler.onError(exception)
    }

    override fun transform(current: T): T {
        return transformHandler.transform(current)
    }
}