package dev.programadorthi.state.core.handler

public fun interface ErrorHandler {
    public fun onError(exception: Throwable)
}