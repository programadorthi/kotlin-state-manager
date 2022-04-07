package dev.programadorthi.core

import dev.programadorthi.core.action.CollectAction
import dev.programadorthi.core.handler.DefaultHandler
import dev.programadorthi.core.handler.ErrorHandler
import dev.programadorthi.core.handler.LifecycleHandler
import dev.programadorthi.core.handler.TransformHandler
import dev.programadorthi.core.platform.Closeable

interface ValueManager<T> : Closeable {
    val closed: Boolean

    val value: T

    fun collect(action: CollectAction<T>)

    fun update(value: T)

    companion object {
        operator fun <T> invoke(
            initialValue: T,
            errorHandler: ErrorHandler = DefaultHandler<T>(),
            lifecycleHandler: LifecycleHandler<T> = DefaultHandler(),
            transformHandler: TransformHandler<T> = DefaultHandler()
        ): ValueManager<T> = ValueManagerImpl(
            initialValue = initialValue,
            errorHandler = errorHandler,
            lifecycleHandler = lifecycleHandler,
            transformHandler = transformHandler,
        )
    }
}
