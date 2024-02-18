package dev.programadorthi.state.compose

import androidx.compose.runtime.SnapshotMutationPolicy
import dev.programadorthi.state.core.handler.ChangeHandler
import dev.programadorthi.state.core.handler.ErrorHandler
import kotlin.coroutines.CoroutineContext

internal class ComposeValueManagerImpl<T>(
    initialValue: T,
    policy: SnapshotMutationPolicy<T>,
    coroutineContext: CoroutineContext,
    private val errorHandler: ErrorHandler,
    private val changeHandler: ChangeHandler<T>,
) : BaseComposeValueManager<T>(initialValue, policy, coroutineContext) {

    override fun onChanged(previous: T, next: T) {
        super.onChanged(previous, next)
        changeHandler.onChanged(previous = previous, next = next)
    }

    override fun onError(exception: Throwable) {
        super.onError(exception)
        errorHandler.onError(exception)
    }
}