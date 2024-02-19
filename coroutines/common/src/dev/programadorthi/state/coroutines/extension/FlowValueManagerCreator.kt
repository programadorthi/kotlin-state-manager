package dev.programadorthi.state.coroutines.extension

import dev.programadorthi.state.core.ValueManager
import dev.programadorthi.state.core.action.ChangeAction
import dev.programadorthi.state.core.action.ErrorAction
import dev.programadorthi.state.core.validation.Validator
import dev.programadorthi.state.core.validation.ValidatorManager
import dev.programadorthi.state.coroutines.BaseFlowValueManager
import dev.programadorthi.state.coroutines.BasicFlowValueManager

public fun <T> flowValueManager(
    initialValue: T,
    validators: List<Validator<T>>? = null,
    changeHandler: ChangeAction<T>? = null,
    errorHandler: ErrorAction? = null,
): BaseFlowValueManager<T> = BasicFlowValueManager(initialValue = initialValue).apply {
    validators?.forEach { addValidator(it) }
    changeHandler?.let { onChanged(it) }
    errorHandler?.let { onError(it) }
}

public fun <T> ValueManager<T>.asFlow(
    validators: List<Validator<T>>? = null,
    changeHandler: ChangeAction<T>? = null,
    errorHandler: ErrorAction? = null,
): BaseFlowValueManager<T> {
    val instance = this

    if (instance is BaseFlowValueManager) {
        return instance
    }

    val valueManager = flowValueManager(
        initialValue = value,
        validators = validators,
        changeHandler = changeHandler,
        errorHandler = errorHandler
    ) as BasicFlowValueManager

    onError {
        valueManager.notifyParentError(it)
    }

    onChanged { previous, next ->
        valueManager.notifyParentChanged(previous, next)
    }

    collect { newValue ->
        valueManager.notifyParentCollector(newValue)
    }

    if (instance is ValidatorManager<*>) {
        instance.onValidated { messages ->
            valueManager.notifyParentValidator(messages)
        }
    }

    return valueManager
}
