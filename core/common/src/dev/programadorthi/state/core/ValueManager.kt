package dev.programadorthi.state.core

import androidx.compose.runtime.snapshots.SnapshotMutableState
import dev.programadorthi.state.core.action.CollectAction
import dev.programadorthi.state.core.action.UpdateAction

@OptIn(ExperimentalStdlibApi::class)
public interface ValueManager<T> : AutoCloseable, SnapshotMutableState<T> {
    public val closed: Boolean

    public fun collect(action: CollectAction<T>)

    public fun update(action: UpdateAction<T>)
}
