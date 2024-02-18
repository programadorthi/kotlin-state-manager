package dev.programadorthi.state.core

import dev.programadorthi.state.core.action.CollectAction
import dev.programadorthi.state.core.action.UpdateAction
import dev.programadorthi.state.core.validation.ValidatorManager

@OptIn(ExperimentalStdlibApi::class)
public interface ValueManager<T> : AutoCloseable, ValidatorManager<T> {
    public val closed: Boolean

    public var value: T

    public operator fun component1(): T

    public operator fun component2(): (T) -> Unit

    public fun collect(action: CollectAction<T>)

    public fun update(action: UpdateAction<T>)
}
