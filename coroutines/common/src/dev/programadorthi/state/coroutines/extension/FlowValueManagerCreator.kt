package dev.programadorthi.state.coroutines.extension

import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.structuralEqualityPolicy
import dev.programadorthi.state.core.handler.ChangeHandler
import dev.programadorthi.state.core.handler.DefaultHandler
import dev.programadorthi.state.core.handler.ErrorHandler
import dev.programadorthi.state.coroutines.FlowValueManager
import dev.programadorthi.state.coroutines.FlowValueManagerImpl
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

public fun <T> flowValueManager(
    initialValue: T,
    coroutineContext: CoroutineContext = Dispatchers.Default,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
    errorHandler: ErrorHandler = DefaultHandler<T>(),
    changeHandler: ChangeHandler<T> = DefaultHandler(),
): FlowValueManager<T> = FlowValueManagerImpl(
    initialValue = initialValue,
    coroutineContext = coroutineContext,
    policy = policy,
    errorHandler = errorHandler,
    changeHandler = changeHandler,
)