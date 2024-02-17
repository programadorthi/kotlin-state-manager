package dev.programadorthi.state.core.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.structuralEqualityPolicy
import dev.programadorthi.state.core.BaseValueManager
import dev.programadorthi.state.core.BasicValueManager
import dev.programadorthi.state.core.handler.ChangeHandler
import dev.programadorthi.state.core.handler.DefaultHandler
import dev.programadorthi.state.core.handler.ErrorHandler
import dev.programadorthi.state.core.validation.Validator

public fun <T> basicValueManager(
    initialValue: T,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
    validators: List<Validator<T>> = emptyList(),
    errorHandler: ErrorHandler = DefaultHandler<T>(),
    changeHandler: ChangeHandler<T> = DefaultHandler(),
): BaseValueManager<T> = BasicValueManager(
    initialValue = initialValue,
    policy = policy,
    errorHandler = errorHandler,
    changeHandler = changeHandler,
).also {
    it += validators
}

@Composable
public fun <T> rememberBasicValueManager(
    initialValue: T,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
    validators: List<Validator<T>> = emptyList(),
    errorHandler: ErrorHandler = DefaultHandler<T>(),
    changeHandler: ChangeHandler<T> = DefaultHandler(),
): BaseValueManager<T> = remember {
    basicValueManager(
        initialValue = initialValue,
        policy = policy,
        validators = validators,
        errorHandler = errorHandler,
        changeHandler = changeHandler,
    )
}