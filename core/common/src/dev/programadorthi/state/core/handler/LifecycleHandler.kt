package dev.programadorthi.state.core.handler

public fun interface AfterChangeLifecycleHandler<in T> {
    public fun onAfterChange(previous: T, current: T)
}

public fun interface BeforeChangeLifecycleHandler<in T> {
    public fun onBeforeChange(current: T, next: T)
}