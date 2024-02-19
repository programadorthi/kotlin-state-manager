package dev.programadorthi.state.coroutines

import dev.programadorthi.state.core.ValueManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class ValueManagerFlow<T>(
    private val valueManager: ValueManager<T>
) : MutableStateFlow<T> {

    private val stateFlow = MutableStateFlow(valueManager.value)

    init {
        valueManager.collect { newValue ->
            stateFlow.tryEmit(newValue)
        }
    }

    override val replayCache: List<T>
        get() = stateFlow.replayCache

    override val subscriptionCount: StateFlow<Int>
        get() = stateFlow.subscriptionCount

    override var value: T
        get() = stateFlow.value
        set(value) {
            valueManager.value = value
        }

    override suspend fun collect(collector: FlowCollector<T>): Nothing {
        stateFlow.collect(collector)
    }

    override fun compareAndSet(expect: T, update: T): Boolean {
        if (stateFlow.value != expect) {
            return false
        }
        value = update
        return true
    }

    override suspend fun emit(value: T) {
        this.value = value
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun resetReplayCache() {
        stateFlow.resetReplayCache()
    }

    override fun tryEmit(value: T): Boolean {
        this.value = value
        return true
    }
}