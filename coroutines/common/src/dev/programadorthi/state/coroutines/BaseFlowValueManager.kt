package dev.programadorthi.state.coroutines

import androidx.compose.runtime.SnapshotMutationPolicy
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
    policy: SnapshotMutationPolicy<T>,
) : BaseValueManager<T>(initialValue, policy), FlowValueManager<T> {

    private val scope = CoroutineScope(coroutineContext)
    private val stateFlow = MutableStateFlow(initialValue)
    private var collectJob: Job? = null

    override val closed: Boolean
        get() = scope.isActive.not()

    override val replayCache: List<T>
        get() = stateFlow.replayCache

    override var value: T
        get() = stateFlow.value
        set(value) = update { value }

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