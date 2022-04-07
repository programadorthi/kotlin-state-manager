package dev.programadorthi.compose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.programadorthi.coroutines.BaseFlowValueManager
import kotlinx.coroutines.CoroutineDispatcher

abstract class BaseComposeValueManager<T>(
    initialValue: T,
    coroutineDispatcher: CoroutineDispatcher
) : BaseFlowValueManager<T>(initialValue, coroutineDispatcher), ComposeValueManager<T> {

    private var state by mutableStateOf(initialValue)

    override val value: T
        get() = state

    override fun commit(value: T): Boolean {
        // calling super to emit value in the StateFlow
        return super.commit(value).also {
            state = value
        }
    }
}