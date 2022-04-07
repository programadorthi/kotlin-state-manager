package dev.programadorthi.coroutines

import dev.programadorthi.core.ValueManager
import kotlinx.coroutines.flow.StateFlow

interface CoroutineValueManager<T> : ValueManager<T>, StateFlow<T>