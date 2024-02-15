package dev.programadorthi.state.core

import dev.programadorthi.state.core.action.CollectAction
import dev.programadorthi.state.core.platform.Closeable

public interface ValueManager<T> : Closeable {
    public val closed: Boolean

    public val value: T

    public fun collect(action: CollectAction<T>)

    public fun update(newValue: T)
}
