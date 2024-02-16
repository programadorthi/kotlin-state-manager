package dev.programadorthi.state.core

import androidx.compose.runtime.SnapshotMutationPolicy
import dev.programadorthi.state.core.handler.AfterChangeLifecycleHandler
import dev.programadorthi.state.core.handler.BeforeChangeLifecycleHandler
import dev.programadorthi.state.core.handler.ErrorHandler

internal class BasicValueManager<T>(
    initialValue: T,
    policy: SnapshotMutationPolicy<T>,
    private val errorHandler: ErrorHandler,
    private val onAfterChange: AfterChangeLifecycleHandler<T>,
    private val onBeforeChange: BeforeChangeLifecycleHandler<T>
) : BaseValueManager<T>(initialValue, policy) {

    override fun onAfterChange(previous: T, current: T) {
        super.onAfterChange(previous, current)
        onAfterChange.onAfterChange(previous, current)
    }

    override fun onBeforeChange(current: T, next: T) {
        super.onBeforeChange(current, next)
        onBeforeChange.onBeforeChange(current, next)
    }

    override fun onError(exception: Throwable) {
        super.onError(exception)
        errorHandler.onError(exception)
    }
}