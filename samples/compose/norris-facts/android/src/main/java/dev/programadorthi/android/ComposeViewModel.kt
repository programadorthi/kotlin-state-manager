package dev.programadorthi.android

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dev.programadorthi.state.compose.composeValueManager

class ComposeViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private var composeState by composeValueManager(0, savedStateHandle)

    val state: Int
        get() = composeState

    fun increment() {
        composeState++
    }

    fun decrement() {
        composeState--
    }
}