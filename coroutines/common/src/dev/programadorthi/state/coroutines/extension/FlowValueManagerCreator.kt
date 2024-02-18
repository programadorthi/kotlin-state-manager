package dev.programadorthi.state.coroutines.extension

import dev.programadorthi.state.core.handler.ChangeHandler
import dev.programadorthi.state.core.handler.DefaultHandler
import dev.programadorthi.state.core.handler.ErrorHandler
import dev.programadorthi.state.coroutines.FlowValueManager
import dev.programadorthi.state.coroutines.FlowValueManagerImpl
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

public fun <T> flowValueManager(
    initialValue: T,
    coroutineContext: CoroutineContext = Dispatchers.Default,
    errorHandler: ErrorHandler = DefaultHandler<T>(),
    changeHandler: ChangeHandler<T> = DefaultHandler(),
): FlowValueManager<T> = FlowValueManagerImpl(
    initialValue = initialValue,
    coroutineContext = coroutineContext,
    errorHandler = errorHandler,
    changeHandler = changeHandler,
)
