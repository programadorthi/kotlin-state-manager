package dev.programadorthi.coroutines.extension

import dev.programadorthi.core.handler.DefaultHandler
import dev.programadorthi.core.handler.ErrorHandler
import dev.programadorthi.core.handler.LifecycleHandler
import dev.programadorthi.core.handler.TransformHandler
import dev.programadorthi.coroutines.CoroutineValueManager
import dev.programadorthi.coroutines.CoroutineValueManagerImpl
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun <T> coroutineValueManager(
    initialValue: T,
    errorHandler: ErrorHandler = DefaultHandler<T>(),
    lifecycleHandler: LifecycleHandler<T> = DefaultHandler(),
    transformHandler: TransformHandler<T> = DefaultHandler(),
    coroutineContext: CoroutineContext = EmptyCoroutineContext
): CoroutineValueManager<T> = CoroutineValueManagerImpl(
    initialValue = initialValue,
    errorHandler = errorHandler,
    lifecycleHandler = lifecycleHandler,
    transformHandler = transformHandler,
    coroutineContext = coroutineContext
)