package dev.programadorthi.state.core.extension

import dev.programadorthi.state.core.BasicValueManager
import dev.programadorthi.state.core.ValueManager
import dev.programadorthi.state.core.handler.AfterChangeLifecycleHandler
import dev.programadorthi.state.core.handler.BeforeChangeLifecycleHandler
import dev.programadorthi.state.core.handler.DefaultHandler
import dev.programadorthi.state.core.handler.ErrorHandler
import dev.programadorthi.state.core.handler.TransformHandler

public fun <T> basicValueManager(
    initialValue: T,
    errorHandler: ErrorHandler = DefaultHandler<T>(),
    onAfterChange: AfterChangeLifecycleHandler<T> = DefaultHandler(),
    onBeforeChange: BeforeChangeLifecycleHandler<T> = DefaultHandler(),
    transformHandler: TransformHandler<T> = DefaultHandler()
): ValueManager<T> = BasicValueManager(
    initialValue = initialValue,
    errorHandler = errorHandler,
    onAfterChange = onAfterChange,
    onBeforeChange = onBeforeChange,
    transformHandler = transformHandler,
)