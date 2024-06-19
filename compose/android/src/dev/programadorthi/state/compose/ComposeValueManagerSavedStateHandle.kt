package dev.programadorthi.state.compose

import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.lifecycle.SavedStateHandle
import dev.programadorthi.state.core.ValueManager

internal class ComposeValueManagerSavedStateHandle<T>(
    private val valueManager: ValueManager<T>,
    private val policy: SnapshotMutationPolicy<T>,
    private val savedStateHandle: SavedStateHandle,
    stateRestorationKey: String,
    saver: Saver<T, out Any>,
) : SaverScope, ValueManager<T> by valueManager {

    private val valueManagerSaver = ValueManagerSaver(saver) { valueManager }
    private val state = mutableStateOf(valueManager.value, policy)
    private val key = "$KEY:$stateRestorationKey"
    private val canBeSaved: Boolean

    init {
        val restoredValue = savedStateHandle.remove<Any>(key)

        // By default SaveStateHandle throw exceptions when can't save the value in a Bundle
        savedStateHandle[key] = valueManager.value
        canBeSaved = true

        valueManager.collect { newValue ->
            state.value = newValue
            savedStateHandle[key] = with(valueManagerSaver) {
                save(valueManager)
            }
        }

        if (restoredValue != null) {
            valueManagerSaver.restore(restoredValue)
        }
    }

    override var value: T
        get() = state.value
        set(value) {
            val current = state.value
            if (policy.equivalent(current, value).not()) {
                valueManager.value = value
            }
        }

    override fun canBeSaved(value: Any): Boolean = canBeSaved

    private companion object {
        private const val KEY = "dev.programadorthi.state.compose.ComposeValueManagerState.android"
    }
}