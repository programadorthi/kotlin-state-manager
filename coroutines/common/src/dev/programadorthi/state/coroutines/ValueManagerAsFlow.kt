package dev.programadorthi.state.coroutines

import dev.programadorthi.state.core.ValueManager
import dev.programadorthi.state.core.validation.ValidatorManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.reflect.KProperty

public operator fun <T> MutableStateFlow<T>.getValue(thisObj: Any?, property: KProperty<*>): T =
    value

public operator fun <T> MutableStateFlow<T>.setValue(
    thisObj: Any?,
    property: KProperty<*>,
    value: T,
) {
    tryEmit(value)
}

public fun <T> ValueManager<T>.asMutableStateFlow(): MutableStateFlow<T> = ValueManagerFlow(this)

public fun <T> ValidatorManager<T>.asStateFlow(): StateFlow<ValidatorResult> =
    ValidatorManagerFlow(this)
