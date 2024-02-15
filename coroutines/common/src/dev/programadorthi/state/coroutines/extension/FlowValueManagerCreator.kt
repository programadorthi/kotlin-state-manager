package dev.programadorthi.state.coroutines.extension

import dev.programadorthi.state.core.handler.AfterChangeLifecycleHandler
import dev.programadorthi.state.core.handler.BeforeChangeLifecycleHandler
import dev.programadorthi.state.core.handler.DefaultHandler
import dev.programadorthi.state.core.handler.ErrorHandler
import dev.programadorthi.state.core.handler.TransformHandler
import dev.programadorthi.state.coroutines.FlowValueManager
import dev.programadorthi.state.coroutines.FlowValueManagerImpl
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

public fun <T> flowValueManager(
    initialValue: T,
    coroutineContext: CoroutineContext = Dispatchers.Default,
    errorHandler: ErrorHandler = DefaultHandler<T>(),
    onAfterChange: AfterChangeLifecycleHandler<T> = DefaultHandler(),
    onBeforeChange: BeforeChangeLifecycleHandler<T> = DefaultHandler(),
    transformHandler: TransformHandler<T> = DefaultHandler()
): FlowValueManager<T> = FlowValueManagerImpl(
    initialValue = initialValue,
    coroutineContext = coroutineContext,
    errorHandler = errorHandler,
    onAfterChange = onAfterChange,
    onBeforeChange = onBeforeChange,
    transformHandler = transformHandler,
)