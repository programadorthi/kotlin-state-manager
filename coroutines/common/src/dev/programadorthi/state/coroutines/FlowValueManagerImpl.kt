package dev.programadorthi.state.coroutines

import dev.programadorthi.state.core.handler.ChangeHandler
import dev.programadorthi.state.core.handler.ErrorHandler
import kotlin.coroutines.CoroutineContext

internal class FlowValueManagerImpl<T>(
    initialValue: T,
    coroutineContext: CoroutineContext,
    private val errorHandler: ErrorHandler,
    private val changeHandler: ChangeHandler<T>,
) : BaseFlowValueManager<T>(initialValue, coroutineContext) {

    override fun onChanged(previous: T, next: T) {
        super.onChanged(previous, next)
        changeHandler.onChanged(previous = previous, next = next)
    }

    override fun onError(exception: Throwable) {
        super.onError(exception)
        errorHandler.onError(exception)
    }
}