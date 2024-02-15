package dev.programadorthi.state.core.handler

internal class DefaultHandler<T> : ErrorHandler,
    AfterChangeLifecycleHandler<T>,
    BeforeChangeLifecycleHandler<T>,
    TransformHandler<T> {

    override fun onError(exception: Throwable) {}

    override fun onAfterChange(previous: T, current: T) {}

    override fun onBeforeChange(current: T, next: T) {}

    override fun transform(current: T): T = current
}