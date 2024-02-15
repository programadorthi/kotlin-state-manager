package dev.programadorthi.state.core.handler

public interface TransformHandler<T> {
    public fun transform(current: T): T = current
}