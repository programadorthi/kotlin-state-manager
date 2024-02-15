package dev.programadorthi.state.coroutines

import dev.programadorthi.state.core.ValueManager
import kotlinx.coroutines.flow.StateFlow

public interface FlowValueManager<T> : ValueManager<T>, StateFlow<T>