package dev.programadorthi.state.compose.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.structuralEqualityPolicy
import dev.programadorthi.state.compose.BasicComposeValueManager
import dev.programadorthi.state.compose.ComposeValueManager
import dev.programadorthi.state.core.ValueManager
import dev.programadorthi.state.core.action.ChangeAction
import dev.programadorthi.state.core.action.ErrorAction
import dev.programadorthi.state.core.extension.plusAssign
import dev.programadorthi.state.core.validation.Validator

public fun <T> composeValueManager(
    initialValue: T,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
    validators: List<Validator<T>> = emptyList(),
    changeHandler: ChangeAction<T> = { _, _ -> },
    errorHandler: ErrorAction = { },
): ComposeValueManager<T> {
    val valueManager = BasicComposeValueManager(initialValue = initialValue, policy = policy)
    valueManager += validators
    valueManager.onChanged(changeHandler)
    valueManager.onError(errorHandler)
    return valueManager
}

@Composable
public fun <T> rememberComposeValueManager(
    initialValue: T,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
    validators: List<Validator<T>> = emptyList(),
    changeHandler: ChangeAction<T> = { _, _ -> },
    errorHandler: ErrorAction = { },
): ComposeValueManager<T> = remember(key1 = initialValue, key2 = policy) {
    composeValueManager(
        initialValue = initialValue,
        policy = policy,
        validators = validators,
        errorHandler = errorHandler,
        changeHandler = changeHandler,
    )
}

public fun <T> ValueManager<T>.asCompose(
    validators: List<Validator<T>> = emptyList(),
    changeHandler: ChangeAction<T> = { _, _ -> },
    errorHandler: ErrorAction = { },
): ComposeValueManager<T> {
    if (this is ComposeValueManager) {
        return this
    }

    val composeValueManager = composeValueManager(
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
        composeValueManager.value = newValue
    }

    return composeValueManager
}
