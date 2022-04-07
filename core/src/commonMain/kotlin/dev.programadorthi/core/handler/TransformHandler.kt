package dev.programadorthi.core.handler

interface TransformHandler<T> {
    fun transform(current: T): T = current
}