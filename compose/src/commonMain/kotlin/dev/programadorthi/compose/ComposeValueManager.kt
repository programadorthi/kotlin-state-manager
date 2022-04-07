package dev.programadorthi.compose

import dev.programadorthi.core.ValueManager
import dev.programadorthi.coroutines.FlowValueManager

interface ComposeValueManager<T> : ValueManager<T>, FlowValueManager<T>