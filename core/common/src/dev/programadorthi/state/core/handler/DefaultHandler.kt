package dev.programadorthi.state.core.handler

public class DefaultHandler<T> : ErrorHandler,
    AfterChangeLifecycleHandler<T>,
    BeforeChangeLifecycleHandler<T> {

    override fun onError(exception: Throwable) {}

    override fun onAfterChange(previous: T, current: T) {}

    override fun onBeforeChange(current: T, next: T) {}
}