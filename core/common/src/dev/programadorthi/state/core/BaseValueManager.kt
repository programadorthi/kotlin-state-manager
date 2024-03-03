package dev.programadorthi.state.core

import dev.programadorthi.state.core.action.ChangeAction
import dev.programadorthi.state.core.action.CollectAction
import dev.programadorthi.state.core.action.ErrorAction
import dev.programadorthi.state.core.action.UpdateAction
import dev.programadorthi.state.core.validation.Validator
import dev.programadorthi.state.core.validation.ValidatorAction
import dev.programadorthi.state.core.validation.ValidatorManager

public abstract class BaseValueManager<T>(
    initialValue: T
) : ValueManager<T>, ValidatorManager<T> {

    private val changeActions = mutableListOf<ChangeAction<T>>()
    private val collectorActions = mutableListOf<CollectAction<T>>()
    private val errorActions = mutableListOf<ErrorAction>()
    private val validatorActions = mutableListOf<ValidatorAction>()
    private val validators = mutableListOf<Validator<T>>()
    private val localMessages = mutableListOf<String>()

    private var opened: Boolean = true
    private var valid = true

    override val closed: Boolean
        get() = !opened

    override val isValid: Boolean
        get() = valid

    override val messages: List<String>
        get() = localMessages.toList()

    override var value: T = initialValue
        set(value) {
            check(!closed) {
                "Manager is closed and can't update the value"
            }
            val previous = field
            if (previous == value) return
            field = value
            runCatching {
                notifyChanged(previous = previous, next = field)
            }.onFailure(::notifyError)
            runCatching {
                notifyCollector(field)
            }.onFailure(::notifyError)
        }

    override fun equals(other: Any?): Boolean =
        other is ValueManager<*> && other.value == value

    override fun hashCode(): Int = value?.hashCode() ?: 0

    override fun component1(): T = value

    override fun component2(): (T) -> Unit = { value = it }

    override fun close() {
        opened = false
        changeActions.clear()
        collectorActions.clear()
        errorActions.clear()
        validators.clear()
        localMessages.clear()
    }

    override fun collect(action: CollectAction<T>) {
        collectorActions += action
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
            if (previous == newValue) {
                return
            }
            validate(newValue)
            newValue
        }.onFailure(::notifyError)
            .onSuccess { newValue ->
                if (isValid) {
                    value = newValue
                }
            }
    }

    override fun validate(): Boolean {
        runCatching {
            validate(value)
        }.onFailure(::notifyError)
        return isValid
    }

    override fun onChanged(action: ChangeAction<T>) {
        changeActions += action
    }

    override fun onError(action: ErrorAction) {
        errorActions += action
    }

    override fun onValidated(action: ValidatorAction) {
        validatorActions += action
    }

    private fun notifyChanged(previous: T, next: T) {
        changeActions.forEach { action -> action(previous, next) }
    }

    private fun notifyCollector(value: T) {
        collectorActions.forEach { action -> action(value) }
    }

    private fun notifyError(throwable: Throwable) {
        errorActions.forEach { action -> action(throwable) }
    }

    private fun notifyValidator(messages: List<String>) {
        validatorActions.forEach { action -> action(messages) }
    }

    private fun validate(value: T) {
        val mappedMessages = validators
            .filter { validator -> validator.isValid(value).not() }
            .map { validator -> validator.message(value) }
        localMessages.clear()
        localMessages.addAll(mappedMessages)
        valid = mappedMessages.isEmpty()
        notifyValidator(localMessages.toList())
    }
}
