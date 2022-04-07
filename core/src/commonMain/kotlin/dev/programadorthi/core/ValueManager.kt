package dev.programadorthi.core

import dev.programadorthi.core.action.CollectAction
import dev.programadorthi.core.platform.Closeable

interface ValueManager<T> : Closeable {
    val closed: Boolean

    val value: T

    fun collect(action: CollectAction<T>)

    fun update(newValue: T)
}
