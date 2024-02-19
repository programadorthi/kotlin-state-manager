package dev.programadorthi.state.coroutines

import dev.programadorthi.state.core.BaseValueManager
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow

public abstract class BaseFlowValueManager<T>(
    initialValue: T,
) : BaseValueManager<T>(initialValue), FlowValueManager<T> {

    private val stateFlow = MutableStateFlow(initialValue)

    override val replayCache: List<T>
        get() = stateFlow.replayCache

    override var value: T
        get() = stateFlow.value
        set(value) {
            super.value = value
            if (super.value == value) {
                stateFlow.tryEmit(value)
            }
        }

    override suspend fun collect(collector: FlowCollector<T>): Nothing {
        stateFlow.collect(collector)
    }
}