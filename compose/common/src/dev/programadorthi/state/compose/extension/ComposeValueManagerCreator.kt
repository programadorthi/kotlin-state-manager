package dev.programadorthi.state.compose.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.structuralEqualityPolicy
import dev.programadorthi.state.compose.BaseComposeValueManager
import dev.programadorthi.state.compose.BasicComposeValueManager
import dev.programadorthi.state.core.ValueManager
import dev.programadorthi.state.core.action.ChangeAction
import dev.programadorthi.state.core.action.ErrorAction
import dev.programadorthi.state.core.validation.Validator
import dev.programadorthi.state.core.validation.ValidatorManager

public fun <T> composeValueManager(
    initialValue: T,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
    validators: List<Validator<T>>? = null,
    changeHandler: ChangeAction<T>? = null,
    errorHandler: ErrorAction? = null,
): BaseComposeValueManager<T> = BasicComposeValueManager(
    initialValue = initialValue,
    policy = policy,
).apply {
    validators?.forEach { addValidator(it) }
    changeHandler?.let { onChanged(it) }
    errorHandler?.let { onError(it) }
}

@Composable
public fun <T> rememberComposeValueManager(
    initialValue: T,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
    validators: List<Validator<T>>? = null,
    changeHandler: ChangeAction<T>? = null,
    errorHandler: ErrorAction? = null,
): BaseComposeValueManager<T> = remember(key1 = initialValue, key2 = policy) {
    composeValueManager(
        initialValue = initialValue,
        policy = policy,
        validators = validators,
        errorHandler = errorHandler,
        changeHandler = changeHandler,
    )
}

public fun <T> ValueManager<T>.asState(
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
    validators: List<Validator<T>>? = null,
    changeHandler: ChangeAction<T>? = null,
    errorHandler: ErrorAction? = null,
): BaseComposeValueManager<T> {
    val instance = this

    if (instance is BaseComposeValueManager) {
        return instance
    }

    val valueManager = composeValueManager(
        initialValue = value,
        policy = policy,
        validators = validators,
        changeHandler = changeHandler,
        errorHandler = errorHandler
    ) as BasicComposeValueManager

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
