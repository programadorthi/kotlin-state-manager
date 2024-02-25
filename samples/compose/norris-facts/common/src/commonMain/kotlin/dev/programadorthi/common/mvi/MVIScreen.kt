package dev.programadorthi.common.mvi

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.LocalSaveableStateRegistry

@Composable
fun MVIScreen() {
    Column {
        val saveableStateRegistry = LocalSaveableStateRegistry.current ?: error(
            "No saveable state registry"
        )
        val mviViewModel = remember { MVIViewModel(saveableStateRegistry) }
        val state = mviViewModel.viewState()

        Button(onClick = { mviViewModel.onEvent(Event.ChangeName) }) {
            Text("Name: ${state.name}")
        }
        Button(onClick = { mviViewModel.onEvent(Event.Decrement) }) {
            Text("Decrement: ${state.age}")
        }
        Button(onClick = { mviViewModel.onEvent(Event.Increment) }) {
            Text("Increment: ${state.age}")
        }
    }
}