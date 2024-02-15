package dev.programadorthi.state.coroutines

import dev.programadorthi.state.core.handler.AfterChangeLifecycleHandler
import dev.programadorthi.state.core.handler.BeforeChangeLifecycleHandler
import dev.programadorthi.state.core.handler.ErrorHandler
import dev.programadorthi.state.core.handler.TransformHandler
import kotlin.coroutines.CoroutineContext

internal class FlowValueManagerImpl<T>(
    initialValue: T,
    coroutineContext: CoroutineContext,
    private val errorHandler: ErrorHandler,
    private val onAfterChange: AfterChangeLifecycleHandler<T>,
    private val onBeforeChange: BeforeChangeLifecycleHandler<T>,
    private val transformHandler: TransformHandler<T>,
) : BaseFlowValueManager<T>(initialValue, coroutineContext) {

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

    override fun transform(current: T): T {
        return transformHandler.transform(current)
    }
}