package dev.programadorthi.state.core.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.structuralEqualityPolicy
import dev.programadorthi.state.core.BasicValueManager
import dev.programadorthi.state.core.ValueManager
import dev.programadorthi.state.core.handler.AfterChangeLifecycleHandler
import dev.programadorthi.state.core.handler.BeforeChangeLifecycleHandler
import dev.programadorthi.state.core.handler.DefaultHandler
import dev.programadorthi.state.core.handler.ErrorHandler

public fun <T> basicValueManager(
    initialValue: T,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
    errorHandler: ErrorHandler = DefaultHandler<T>(),
    onAfterChange: AfterChangeLifecycleHandler<T> = DefaultHandler(),
    onBeforeChange: BeforeChangeLifecycleHandler<T> = DefaultHandler()
): ValueManager<T> = BasicValueManager(
    initialValue = initialValue,
    policy = policy,
    errorHandler = errorHandler,
    onAfterChange = onAfterChange,
    onBeforeChange = onBeforeChange,
)

@Composable
public fun <T> rememberBasicValueManager(
    initialValue: T,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
    errorHandler: ErrorHandler = DefaultHandler<T>(),
    onAfterChange: AfterChangeLifecycleHandler<T> = DefaultHandler(),
    onBeforeChange: BeforeChangeLifecycleHandler<T> = DefaultHandler()
): ValueManager<T> = remember {
    basicValueManager(
        initialValue = initialValue,
        policy = policy,
        errorHandler = errorHandler,
        onAfterChange = onAfterChange,
        onBeforeChange = onBeforeChange,
    )
}