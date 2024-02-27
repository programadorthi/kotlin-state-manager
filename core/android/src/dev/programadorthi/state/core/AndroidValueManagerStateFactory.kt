package dev.programadorthi.state.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras

internal class AndroidValueManagerStateFactory<T>(
    private val valueManager: BaseValueManager<T>,
    private val stateRestorationKey: String,
    private val saver: AndroidValueManagerStateSaver<T>?,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return AndroidValueManagerState(
            valueManager = valueManager,
            stateRestorationKey = stateRestorationKey,
            savedStateHandle = extras.createSavedStateHandle(),
            saver = saver,
        ) as T
    }

}