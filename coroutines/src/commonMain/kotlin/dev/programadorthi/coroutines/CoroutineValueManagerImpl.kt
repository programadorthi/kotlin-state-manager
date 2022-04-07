package dev.programadorthi.coroutines

import dev.programadorthi.core.action.CollectAction
import dev.programadorthi.core.handler.ErrorHandler
import dev.programadorthi.core.handler.LifecycleHandler
import dev.programadorthi.core.handler.TransformHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

internal class CoroutineValueManagerImpl<T>(
    initialValue: T,
    errorHandler: ErrorHandler,
    lifecycleHandler: LifecycleHandler<T>,
    transformHandler: TransformHandler<T>,
    coroutineContext: CoroutineContext
) : CoroutineValueManager<T>,
    ErrorHandler by errorHandler,
    LifecycleHandler<T> by lifecycleHandler,
    TransformHandler<T> by transformHandler {

    private val scope = CoroutineScope(coroutineContext)
    private val stateFlow = MutableStateFlow(initialValue)
    private var collectJob: Job? = null

    override val closed: Boolean
        get() = scope.isActive.not()

    override val replayCache: List<T>
        get() = stateFlow.replayCache

    override val value: T
        get() = stateFlow.value

    override fun close() {
        scope.cancel()
    }

    override fun collect(action: CollectAction<T>) {
        collectJob?.cancel()
        collectJob = scope.launch {
            stateFlow.collect {
                action.invoke(it)
            }
        }
    }

    override suspend fun collect(collector: FlowCollector<T>): Nothing {
        stateFlow.collect(collector)
    }

    override fun update(value: T) {
        runCatching {
            check(scope.isActive) {
                "Coroutine Manager is closed and can't update the value to $value"
            }
            val previous = stateFlow.value
            onBeforeChange(previous, value)
            val newValue = transform(value)
            if (stateFlow.tryEmit(value)) {
                onAfterChange(previous, newValue)
            }
        }.onFailure(::onError)
    }
}