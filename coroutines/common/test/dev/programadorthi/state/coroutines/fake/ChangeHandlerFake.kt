package dev.programadorthi.state.coroutines.fake

import dev.programadorthi.state.core.handler.ChangeHandler

internal class ChangeHandlerFake : ChangeHandler<Int> {
    val events = mutableListOf<Pair<Int, Int>>()

    override fun onChanged(previous: Int, next: Int) {
        events += previous to next
    }
}