package dev.programadorthi.android

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dev.programadorthi.state.core.androidValueManager

class MainViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val range = 'A'..'Z'

    private var nameState by androidValueManager("", savedStateHandle)
    private var ageState by androidValueManager(0, savedStateHandle)

    val name: String
        get() = nameState

    val age: Int
        get() = ageState

    fun decrement() {
        ageState--
        updateName()
    }

    fun increment() {
        ageState++
        updateName()
    }

    private fun updateName() {
        nameState = List(size = ageState.coerceAtLeast(0)) {
            range.random()
        }.joinToString(separator = "")
    }
}