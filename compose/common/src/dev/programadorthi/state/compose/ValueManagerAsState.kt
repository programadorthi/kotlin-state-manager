package dev.programadorthi.state.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.structuralEqualityPolicy
import dev.programadorthi.state.core.ValueManager

public fun <T> ValueManager<T>.asState(
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy()
): MutableState<T> = ValueManagerMutableState(this, policy)

@Composable
public fun <T> rememberValueManager(
    valueManager: () -> ValueManager<T>
): MutableState<T> = remember(valueManager).asState()

@Composable
public fun <T> rememberSaveableValueManager(
    saver: Saver<T, out Any> = autoSaver(),
    valueManager: () -> ValueManager<T>
): ValueManager<T> = rememberSaveable(
    init = valueManager,
    saver = ValueManagerSaver(
        saver = saver,
        manager = valueManager,
    ),
)

@Composable
public fun <T> rememberSaveableValueManagerAsState(
    saver: Saver<T, out Any> = autoSaver(),
    valueManager: () -> ValueManager<T>
): MutableState<T> = rememberSaveableValueManager(
    saver = saver,
    valueManager = valueManager,
).asState()
