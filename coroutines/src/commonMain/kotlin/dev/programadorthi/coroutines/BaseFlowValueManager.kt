package dev.programadorthi.coroutines

import dev.programadorthi.core.BaseValueManager
import dev.programadorthi.core.action.CollectAction
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

abstract class BaseFlowValueManager<T>(
    initialValue: T,
    coroutineDispatcher: CoroutineDispatcher
) : BaseValueManager<T>(initialValue), FlowValueManager<T> {

    private val scope = CoroutineScope(coroutineDispatcher)
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

    override fun commit(value: T): Boolean {
        return stateFlow.tryEmit(value)
    }
}