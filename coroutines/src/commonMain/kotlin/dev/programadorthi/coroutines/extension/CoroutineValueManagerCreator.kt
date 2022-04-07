package dev.programadorthi.coroutines.extension

import dev.programadorthi.core.handler.DefaultHandler
import dev.programadorthi.core.handler.ErrorHandler
import dev.programadorthi.core.handler.LifecycleHandler
import dev.programadorthi.core.handler.TransformHandler
import dev.programadorthi.coroutines.CoroutineValueManager
import dev.programadorthi.coroutines.CoroutineValueManagerImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

fun <T> coroutineValueManager(
    initialValue: T,
    errorHandler: ErrorHandler = DefaultHandler<T>(),
    lifecycleHandler: LifecycleHandler<T> = DefaultHandler(),
    transformHandler: TransformHandler<T> = DefaultHandler(),
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Unconfined
): CoroutineValueManager<T> = CoroutineValueManagerImpl(
    initialValue = initialValue,
    errorHandler = errorHandler,
    lifecycleHandler = lifecycleHandler,
    transformHandler = transformHandler,
    coroutineDispatcher = coroutineDispatcher
)