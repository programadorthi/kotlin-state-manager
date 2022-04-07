package dev.programadorthi.core.handler

interface LifecycleHandler<in T> {
    fun onAfterChange(previous: T, current: T) {}
    fun onBeforeChange(current: T, next: T) {}
}