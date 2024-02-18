package dev.programadorthi.state.compose

import androidx.compose.runtime.snapshots.SnapshotMutableState
import dev.programadorthi.state.coroutines.FlowValueManager

public interface ComposeValueManager<T> : FlowValueManager<T>, SnapshotMutableState<T>