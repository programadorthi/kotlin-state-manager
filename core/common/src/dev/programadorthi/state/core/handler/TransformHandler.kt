package dev.programadorthi.state.core.handler

public fun interface TransformHandler<T> {
    public fun transform(current: T): T
}