package dev.programadorthi.compose

import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.programadorthi.core.action.CollectAction
import dev.programadorthi.core.handler.ErrorHandler
import dev.programadorthi.core.handler.LifecycleHandler
import dev.programadorthi.core.handler.TransformHandler

internal open class ComposeBasicValueManagerImpl<T>(
    initialValue: T,
    errorHandler: ErrorHandler,
    lifecycleHandler: LifecycleHandler<T>,
    transformHandler: TransformHandler<T>
) : ComposeBasicValueManager<T>,
    ErrorHandler by errorHandler,
    LifecycleHandler<T> by lifecycleHandler,
    TransformHandler<T> by transformHandler,
    RememberObserver {

    private var opened = true
    private var collectAction: CollectAction<T>? = null
    protected var state by mutableStateOf(initialValue)

    override val closed: Boolean
        get() = !opened

    override val value: T
        get() = state

    override fun onAbandoned() = Unit

    override fun onForgotten() {
        close()
    }

    override fun onRemembered() = Unit

    override fun close() {
        opened = false
    }

    override fun collect(action: CollectAction<T>) {
        collectAction = action
    }

    override fun update(value: T) {
        runCatching {
            check(opened) {
                "Compose Basic Manager is closed and can't update the value to $value"
            }
            val previous = state
            onBeforeChange(previous, value)
            val newValue = transform(value)
            internalUpdate(previous, newValue)
        }.onFailure(::onError)
    }

    protected open fun internalUpdate(previous: T, newValue: T) {
        state = newValue
        onSuccess(previous, newValue)
    }

    protected fun onSuccess(previous: T, newValue: T) {
        collectAction?.invoke(newValue)
        onAfterChange(previous, newValue)
    }
}