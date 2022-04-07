package dev.programadorthi.coroutines

import dev.programadorthi.core.ValueManager
import kotlinx.coroutines.flow.StateFlow

interface FlowValueManager<T> : ValueManager<T>, StateFlow<T>