package dev.programadorthi.state.core

import dev.programadorthi.state.core.action.ChangeAction
import dev.programadorthi.state.core.action.CollectAction
import dev.programadorthi.state.core.action.ErrorAction
import dev.programadorthi.state.core.action.UpdateAction
import dev.programadorthi.state.core.serialization.ValueManagerSerializer
import kotlinx.serialization.Serializable

@OptIn(ExperimentalStdlibApi::class)
@Serializable(with = ValueManagerSerializer::class)
public interface ValueManager<T> : AutoCloseable {
    public val closed: Boolean

    public var value: T

    public operator fun component1(): T

    public operator fun component2(): (T) -> Unit

    public fun collect(action: CollectAction<T>)

    public fun update(action: UpdateAction<T>)

    public fun onChanged(action: ChangeAction<T>)

    public fun onError(action: ErrorAction)
}
