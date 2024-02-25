package dev.programadorthi.common.mvi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.SaveableStateRegistry
import dev.programadorthi.state.compose.ComposeValueManager
import dev.programadorthi.state.compose.composeValueManager

data class MyState(
    val name: String,
    val age: Int,
)

sealed class Event {
    data object ChangeName : Event()
    data object Decrement : Event()
    data object Increment : Event()
}

fun <T> MVIViewModel.composeValue(value: T): ComposeValueManager<T> =
    composeValueManager(value, stateRegistry = current)

class MVIViewModel(val current: SaveableStateRegistry) {

    private var name by composeValue("")
    private var age by composeValue(20)

    private val range = 'A'..'Z'

    @Composable
    fun viewState(): MyState {
        return MyState(
            name = name,
            age = age,
        )
    }

    fun onEvent(event: Event) {
        when (event) {
            Event.Decrement -> age--
            Event.Increment -> age++
            Event.ChangeName -> name = List(size = age.coerceAtLeast(0)) {
                range.random()
            }.joinToString(separator = "")
        }
        println(">>>> event: $event, age: $age, name: $name")
    }
}