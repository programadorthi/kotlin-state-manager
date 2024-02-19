package dev.programadorthi.state.coroutines.extension

import dev.programadorthi.state.core.ValueManager
import dev.programadorthi.state.core.action.ChangeAction
import dev.programadorthi.state.core.action.ErrorAction
import dev.programadorthi.state.core.extension.plusAssign
import dev.programadorthi.state.core.validation.Validator
import dev.programadorthi.state.coroutines.BasicFlowValueManager
import dev.programadorthi.state.coroutines.FlowValueManager

public fun <T> flowValueManager(
    initialValue: T,
    validators: List<Validator<T>> = emptyList(),
    changeHandler: ChangeAction<T> = { _, _ -> },
    errorHandler: ErrorAction = { },
): FlowValueManager<T> {
    val valueManager = BasicFlowValueManager(initialValue = initialValue)
    valueManager += validators
    valueManager.onChanged(changeHandler)
    valueManager.onError(errorHandler)
    return valueManager
}

public fun <T> ValueManager<T>.asFlow(
    validators: List<Validator<T>> = emptyList(),
    changeHandler: ChangeAction<T> = { _, _ -> },
    errorHandler: ErrorAction = { },
): FlowValueManager<T> {
    if (this is FlowValueManager) {
        return this
    }
    val flow = flowValueManager(
        initialValue = value,
        validators = validators,
        errorHandler = errorHandler,
        changeHandler = changeHandler,
    )

    onError {
        errorHandler(it)
    }

    onChanged { previous, next ->
        changeHandler(previous, next)
    }

    collect { newValue ->
        flow.value = newValue
    }

    return flow
}
