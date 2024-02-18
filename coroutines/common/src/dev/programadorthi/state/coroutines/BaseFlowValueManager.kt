package dev.programadorthi.state.coroutines

import dev.programadorthi.state.core.BaseValueManager
import dev.programadorthi.state.core.action.CollectAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

public abstract class BaseFlowValueManager<T>(
    initialValue: T,
    coroutineContext: CoroutineContext,
) : BaseValueManager<T>(initialValue), FlowValueManager<T> {

    private val scope = CoroutineScope(coroutineContext)
    private val stateFlow = MutableStateFlow(initialValue)
    private var collectJob: Job? = null

    override val closed: Boolean
        get() = scope.isActive.not()

    override val replayCache: List<T>
        get() = stateFlow.replayCache

    override var value: T
        get() = stateFlow.value
        set(value) {
            super.value = value
            stateFlow.tryEmit(value)
        }

    override fun close() {
        scope.cancel()
        super.close()
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
}