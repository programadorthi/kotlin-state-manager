package dev.programadorthi.state.compose.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.structuralEqualityPolicy
import dev.programadorthi.state.compose.ComposeValueManager
import dev.programadorthi.state.compose.ComposeValueManagerImpl
import dev.programadorthi.state.core.handler.ChangeHandler
import dev.programadorthi.state.core.handler.DefaultHandler
import dev.programadorthi.state.core.handler.ErrorHandler
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

public fun <T> composeValueManager(
    initialValue: T,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
    coroutineContext: CoroutineContext = Dispatchers.Default,
    errorHandler: ErrorHandler = DefaultHandler<T>(),
    changeHandler: ChangeHandler<T> = DefaultHandler(),
): ComposeValueManager<T> = ComposeValueManagerImpl(
    initialValue = initialValue,
    policy = policy,
    coroutineContext = coroutineContext,
    errorHandler = errorHandler,
    changeHandler = changeHandler,
)

@Composable
public fun <T> rememberComposeValueManager(
    initialValue: T,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
    coroutineContext: CoroutineContext = Dispatchers.Default,
    errorHandler: ErrorHandler = DefaultHandler<T>(),
    changeHandler: ChangeHandler<T> = DefaultHandler(),
): ComposeValueManager<T> = remember(key1 = initialValue, key2 = policy, key3 = coroutineContext) {
    ComposeValueManagerImpl(
        initialValue = initialValue,
        policy = policy,
        coroutineContext = coroutineContext,
        errorHandler = errorHandler,
        changeHandler = changeHandler,
    )
}
