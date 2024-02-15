package dev.programadorthi.state.core

import dev.programadorthi.state.core.action.CollectAction
import dev.programadorthi.state.core.action.UpdateAction
import dev.programadorthi.state.core.validation.Validator

public abstract class BaseValueManager<T>(initialValue: T) : ValueManager<T> {

    private val localMessages = mutableListOf<String>()
    private val validators = mutableListOf<Validator<T>>()

    private var opened: Boolean = true

    private var currentValue: T = initialValue
    private var collectAction: CollectAction<T>? = null

    override val closed: Boolean
        get() = !opened

    override val isValid: Boolean
        get() = messages.isEmpty()

    override val messages: List<String>
        get() = localMessages

    override val value: T
        get() = currentValue

    override fun close() {
        opened = false
    }

    override fun collect(action: CollectAction<T>) {
        collectAction = action
    }

    override fun addValidator(validator: Validator<T>) {
        validators += validator
    }

    override fun removeValidator(validator: Validator<T>) {
        validators -= validator
    }

    override fun update(action: UpdateAction<T>) {
        runCatching {
            check(opened) {
                "Manager is closed and can't update the value"
            }
            val previous = value
            val newValue = action(previous)
            if (newValue == previous) return
            onBeforeChange(previous, newValue)
            val transformedValue = transform(newValue)
            localMessages.clear()
            localMessages += validators
                .filter { validator -> !validator.isValid(transformedValue) }
                .map { validator -> validator.message(transformedValue) }
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
        if (isValid) {
            currentValue = value
        }
        return isValid
    }
}
