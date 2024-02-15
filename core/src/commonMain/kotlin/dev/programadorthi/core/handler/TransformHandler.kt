package dev.programadorthi.core.handler

public interface TransformHandler<T> {
    public fun transform(current: T): T = current
}