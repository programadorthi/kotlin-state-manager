package dev.programadorthi.core.handler

interface ErrorHandler {
    fun onError(exception: Throwable) {}
}