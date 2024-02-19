package dev.programadorthi.state.compose

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.mutableStateOf
import dev.programadorthi.state.core.ValueManager

internal class ValueManagerMutableState<T>(
    private val valueManager: ValueManager<T>,
    private val policy: SnapshotMutationPolicy<T>,
) : MutableState<T> {

    private val state = mutableStateOf(valueManager.value, policy)

    init {
        valueManager.collect { newValue ->
            state.value = newValue
        }
    }

    override var value: T = valueManager.value
        get() = state.value
        set(value) {
            if (policy.equivalent(field, value).not()) {
                valueManager.value = value
            }
        }

    override fun component1(): T = value

    override fun component2(): (T) -> Unit = { value = it }
}