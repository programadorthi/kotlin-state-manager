package dev.programadorthi.state.core.handler

public class DefaultHandler<T> : ErrorHandler, ChangeHandler<T> {

    override fun onError(exception: Throwable) {}

    override fun onChanged(previous: T, next: T) {}
}