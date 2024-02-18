package dev.programadorthi.state.core

import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
import dev.programadorthi.state.core.action.CollectAction
import dev.programadorthi.state.core.action.UpdateAction
import dev.programadorthi.state.core.handler.ChangeHandler
import dev.programadorthi.state.core.handler.ErrorHandler
import dev.programadorthi.state.core.validation.Validator
import dev.programadorthi.state.core.validation.ValidatorManager

public abstract class BaseValueManager<T>(
    initialValue: T,
    final override val policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
) : ValueManager<T>,
    ValidatorManager<T>,
    ChangeHandler<T>,
    ErrorHandler {

    private val validators = mutableListOf<Validator<T>>()

    private var collectAction: CollectAction<T>? = null
    private var opened: Boolean = true

    private var currentValue by mutableStateOf(initialValue, policy)
    private var valid = mutableStateOf(true)
    private var localMessages = mutableStateOf(emptyList<String>())

    override val closed: Boolean
        get() = !opened

    override val isValid: State<Boolean>
        get() = valid

    override val messages: State<List<String>>
        get() = localMessages

    override var value: T = initialValue
        get() = currentValue
        set(value) {
            check(!closed) {
                "Manager is closed and can't update the value"
            }
            val previous = field
            currentValue = value
            field = value
            onChanged(previous = previous, next = field)
            collectAction?.invoke(field)
        }

    override fun component1(): T = value

    override fun component2(): (T) -> Unit = { value = it }

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
            val previous = value
            val newValue = action(previous)
            if (policy.equivalent(previous, newValue)) {
                return
            }
            validate(newValue)
            if (isValid.value) {
                value = newValue
            }
        }.onFailure(::onError)
    }

    override fun validate(): Boolean {
        validate(value)
        return isValid.value
    }

    override fun onChanged(previous: T, next: T) {}

    override fun onError(exception: Throwable) {}

    private fun validate(value: T) {
        val mappedMessages = validators
            .filter { validator -> validator.isValid(value).not() }
            .map { validator -> validator.message(value) }
        localMessages.value = mappedMessages
        valid.value = mappedMessages.isEmpty()
    }
}
