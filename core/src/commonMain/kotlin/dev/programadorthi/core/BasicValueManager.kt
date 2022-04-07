package dev.programadorthi.core

import dev.programadorthi.core.action.CollectAction
import dev.programadorthi.core.handler.ErrorHandler
import dev.programadorthi.core.handler.LifecycleHandler
import dev.programadorthi.core.handler.TransformHandler

internal class BasicValueManager<T>(
    initialValue: T,
    errorHandler: ErrorHandler,
    lifecycleHandler: LifecycleHandler<T>,
    transformHandler: TransformHandler<T>
) : ValueManager<T>,
    ErrorHandler by errorHandler,
    LifecycleHandler<T> by lifecycleHandler,
    TransformHandler<T> by transformHandler {

    private var opened: Boolean = true
    private var collectAction: CollectAction<T>? = null
    private var currentValue: T = initialValue

    override val closed: Boolean
        get() = !opened

    override val value: T
        get() = currentValue

    override fun close() {
        opened = false
    }

    override fun collect(action: CollectAction<T>) {
        collectAction = action
    }

    override fun update(value: T) {
        runCatching {
            check(opened) {
                "Manager is closed and can't update the value to $value"
            }
            val previous = currentValue
            onBeforeChange(previous, value)
            val newValue = transform(value)
            currentValue = newValue
            collectAction?.invoke(newValue)
            onAfterChange(previous, newValue)
        }.onFailure(::onError)
    }
}