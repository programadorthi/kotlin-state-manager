package dev.programadorthi.state.compose

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.structuralEqualityPolicy
import dev.programadorthi.state.core.ValueManager
import dev.programadorthi.state.core.validation.ValidatorManager

public fun <T> ValueManager<T>.asState(
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy()
): MutableState<T> = ValueManagerMutableState(this, policy)

public fun <T> ValidatorManager<T>.asValidatorState(): ValidatorManagerState =
    ValidatorManagerStateImpl(this)
