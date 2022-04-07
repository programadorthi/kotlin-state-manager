package dev.programadorthi.compose

import dev.programadorthi.core.handler.ErrorHandler
import dev.programadorthi.core.handler.LifecycleHandler
import dev.programadorthi.core.handler.TransformHandler
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow

internal class ComposeFlowValueManagerImpl<T>(
    initialValue: T,
    errorHandler: ErrorHandler,
    lifecycleHandler: LifecycleHandler<T>,
    transformHandler: TransformHandler<T>
) : ComposeBasicValueManagerImpl<T>(
    initialValue = initialValue,
    errorHandler = errorHandler,
    lifecycleHandler = lifecycleHandler,
    transformHandler = transformHandler
), ComposeFlowValueManager<T> {

    private val stateFlow = MutableStateFlow(initialValue)

    override val replayCache: List<T>
        get() = stateFlow.replayCache

    override suspend fun collect(collector: FlowCollector<T>): Nothing {
        stateFlow.collect(collector)
    }

    override fun internalUpdate(previous: T, newValue: T) {
        if (stateFlow.tryEmit(newValue)) {
            state = newValue
            onSuccess(previous, newValue)
        }
    }
}