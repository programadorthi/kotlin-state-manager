package dev.programadorthi.coroutines.extension

import dev.programadorthi.core.handler.DefaultHandler
import dev.programadorthi.core.handler.ErrorHandler
import dev.programadorthi.core.handler.LifecycleHandler
import dev.programadorthi.core.handler.TransformHandler
import dev.programadorthi.coroutines.FlowValueManager
import dev.programadorthi.coroutines.FlowValueManagerImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

fun <T> flowValueManager(
    initialValue: T,
    errorHandler: ErrorHandler = DefaultHandler<T>(),
    lifecycleHandler: LifecycleHandler<T> = DefaultHandler(),
    transformHandler: TransformHandler<T> = DefaultHandler(),
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Unconfined
): FlowValueManager<T> = FlowValueManagerImpl(
    initialValue = initialValue,
    errorHandler = errorHandler,
    lifecycleHandler = lifecycleHandler,
    transformHandler = transformHandler,
    coroutineDispatcher = coroutineDispatcher
)