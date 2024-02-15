package dev.programadorthi.state.core

import dev.programadorthi.state.core.action.CollectAction

public abstract class BaseValueManager<T>(initialValue: T) : ValueManager<T> {

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

    override fun update(newValue: T) {
        runCatching {
            check(opened) {
                "Manager is closed and can't update the value to $newValue"
            }
            val previous = value
            if (newValue == previous) return
            onBeforeChange(previous, newValue)
            val transformedValue = transform(newValue)
            if (commit(transformedValue)) {
                collectAction?.invoke(transformedValue)
                onAfterChange(previous, transformedValue)
            }
        }.onFailure(::onError)
    }

    protected open fun onAfterChange(previous: T, current: T) {}
    protected open fun onBeforeChange(current: T, next: T) {}
    protected open fun onError(exception: Throwable) {}
    protected open fun transform(current: T): T = current
    protected open fun commit(value: T): Boolean {
        currentValue = value
        return true
    }
}
