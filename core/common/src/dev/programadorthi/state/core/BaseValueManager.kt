package dev.programadorthi.state.core

import dev.programadorthi.state.core.action.CollectAction
import dev.programadorthi.state.core.action.UpdateAction
import dev.programadorthi.state.core.handler.ChangeHandler
import dev.programadorthi.state.core.handler.ErrorHandler
import dev.programadorthi.state.core.validation.Validator

public abstract class BaseValueManager<T>(
    initialValue: T,
) : ValueManager<T>, ChangeHandler<T>, ErrorHandler {

    private val validators = mutableListOf<Validator<T>>()
    private val localMessages = mutableListOf<String>()

    private var collectAction: CollectAction<T>? = null
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
            if (previous == newValue) {
                return
            }
            validate(newValue)
            if (isValid) {
                value = newValue
            }
        }.onFailure(::onError)
    }

    override fun validate(): Boolean {
        validate(value)
        return isValid
    }

    override fun onChanged(previous: T, next: T) {}

    override fun onError(exception: Throwable) {}

    private fun validate(value: T) {
        val mappedMessages = validators
            .filter { validator -> validator.isValid(value).not() }
            .map { validator -> validator.message(value) }
        localMessages.clear()
        localMessages.addAll(mappedMessages)
        valid = mappedMessages.isEmpty()
    }
}
