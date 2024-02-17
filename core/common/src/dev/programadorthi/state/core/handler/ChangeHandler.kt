package dev.programadorthi.state.core.handler

public fun interface ChangeHandler<T> {
    public fun onChanged(previous: T, next: T)
}