package dev.programadorthi.state.core.handler

public interface LifecycleHandler<in T> {
    public fun onAfterChange(previous: T, current: T) {}
    public fun onBeforeChange(current: T, next: T) {}
}