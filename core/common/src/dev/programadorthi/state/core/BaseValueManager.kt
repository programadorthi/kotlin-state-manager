package dev.programadorthi.state.core

import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.programadorthi.state.core.action.CollectAction
import dev.programadorthi.state.core.action.UpdateAction
import dev.programadorthi.state.core.validation.Validator

public abstract class BaseValueManager<T>(
    initialValue: T,
    private val policy: SnapshotMutationPolicy<T>,
) : ValueManager<T> {

    private val localMessages = mutableListOf<String>()
    private val validators = mutableListOf<Validator<T>>()

    private var currentValue by mutableStateOf(initialValue, policy)
    private var collectAction: CollectAction<T>? = null
    private var opened: Boolean = true

    override val closed: Boolean
        get() = !opened

    override val isValid: Boolean
        get() = messages.isEmpty()

    override val messages: List<String>
        get() = localMessages

    override var value: T
        get() = currentValue
        set(value) = update { value }

    override fun component1(): T = currentValue

    override fun component2(): (T) -> Unit = { newValue ->
        update { newValue }
    }

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
            if (policy.equivalent(previous, newValue)) {
                return
            }
            onBeforeChange(previous, newValue)
            localMessages.clear()
            localMessages += validators
                .filter { validator -> !validator.isValid(newValue) }
                .map { validator -> validator.message(newValue) }
            if (commit(newValue)) {
                collectAction?.invoke(newValue)
                onAfterChange(previous, newValue)
            }
        }.onFailure(::onError)
    }

    protected open fun onAfterChange(previous: T, current: T) {}
    protected open fun onBeforeChange(current: T, next: T) {}
    protected open fun onError(exception: Throwable) {}
    protected open fun commit(value: T): Boolean {
        if (isValid) {
            currentValue = value
        }
        return isValid
    }
}
