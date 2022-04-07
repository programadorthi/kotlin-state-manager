package dev.programadorthi.compose

import dev.programadorthi.coroutines.FlowValueManager

interface ComposeFlowValueManager<T> : ComposeBasicValueManager<T>, FlowValueManager<T>