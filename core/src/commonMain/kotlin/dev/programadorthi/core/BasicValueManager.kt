package dev.programadorthi.core

import dev.programadorthi.core.handler.ErrorHandler
import dev.programadorthi.core.handler.LifecycleHandler
import dev.programadorthi.core.handler.TransformHandler

internal class BasicValueManager<T>(
    initialValue: T,
    private val errorHandler: ErrorHandler,
    private val lifecycleHandler: LifecycleHandler<T>,
    private val transformHandler: TransformHandler<T>
) : BaseValueManager<T>(initialValue) {

    override fun onAfterChange(previous: T, current: T) {
        super.onAfterChange(previous, current)
        lifecycleHandler.onAfterChange(previous, current)
    }

    override fun onBeforeChange(current: T, next: T) {
        super.onBeforeChange(current, next)
        lifecycleHandler.onBeforeChange(current, next)
    }

    override fun onError(exception: Throwable) {
        super.onError(exception)
        errorHandler.onError(exception)
    }

    override fun transform(current: T): T {
        return transformHandler.transform(current)
    }
}