package dev.programadorthi.state.compose

import androidx.compose.runtime.State
import dev.programadorthi.state.coroutines.FlowValueManager

public interface ComposeValueManager<T> : FlowValueManager<T>, State<T> {

    public val isValidAsState: State<Boolean>

    public val messagesAsState: State<List<String>>

}