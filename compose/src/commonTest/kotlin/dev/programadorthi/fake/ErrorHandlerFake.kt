package dev.programadorthi.fake

import dev.programadorthi.core.handler.ErrorHandler

internal class ErrorHandlerFake : ErrorHandler {
    private var exceptionsList = mutableListOf<Throwable>()
    val exceptions: List<Throwable>
        get() = exceptionsList

    override fun onError(exception: Throwable) {
        exceptionsList += exception
    }
}