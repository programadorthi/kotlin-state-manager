package dev.programadorthi.state.coroutines.fake

import dev.programadorthi.state.core.handler.AfterChangeLifecycleHandler
import dev.programadorthi.state.core.handler.BeforeChangeLifecycleHandler


internal class LifecycleHandlerFake : AfterChangeLifecycleHandler<Int>, BeforeChangeLifecycleHandler<Int> {
    private val eventsList = mutableListOf<LifecycleEvent>()
    val events: List<LifecycleEvent>
        get() = eventsList

    override fun onAfterChange(previous: Int, current: Int) {
        eventsList += LifecycleEvent.After(previous, current)
    }

    override fun onBeforeChange(current: Int, next: Int) {
        eventsList += LifecycleEvent.Before(current, next)
    }

    internal sealed class LifecycleEvent {
        data class After(val current: Int, val next: Int) : LifecycleEvent()
        data class Before(val previous: Int, val current: Int) : LifecycleEvent()
    }
}