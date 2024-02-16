package dev.programadorthi.common.state

sealed class State<out T> {
    data object Loading : State<Nothing>()
    data class Error(val exception: Throwable) : State<Nothing>()
    data class Success<T>(val result: T) : State<T>()
}
