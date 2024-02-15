package dev.programadorthi.state.coroutines.fake

import dev.programadorthi.state.core.handler.ErrorHandler

internal class ErrorHandlerFake : ErrorHandler {
    private var exceptionsList = mutableListOf<Throwable>()
    val exceptions: List<Throwable>
        get() = exceptionsList

    override fun onError(exception: Throwable) {
        exceptionsList += exception
    }
}