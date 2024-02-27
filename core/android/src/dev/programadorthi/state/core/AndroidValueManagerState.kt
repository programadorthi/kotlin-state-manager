package dev.programadorthi.state.core

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

internal class AndroidValueManagerState<T>(
    val valueManager: BaseValueManager<T>,
    stateRestorationKey: String,
    savedStateHandle: SavedStateHandle,
    saver: AndroidValueManagerStateSaver<T>?,
) : ViewModel() {

    init {
        val restored: T? = when (saver) {
            null -> savedStateHandle[stateRestorationKey]
            else -> savedStateHandle.get<Bundle>(stateRestorationKey)?.let { bundle ->
                saver.restore(bundle)
            }
        }

        if (restored != null) {
            valueManager.value = restored
        }

        if (saver == null) {
            valueManager.collect { value ->
                savedStateHandle[stateRestorationKey] = value
            }
        } else {
            savedStateHandle.setSavedStateProvider(stateRestorationKey) {
                saver.save(valueManager.value)
            }
        }
    }
}