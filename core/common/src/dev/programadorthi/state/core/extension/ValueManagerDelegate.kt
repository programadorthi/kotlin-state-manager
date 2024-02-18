package dev.programadorthi.state.core.extension

import dev.programadorthi.state.core.ValueManager
import kotlin.reflect.KProperty

public operator fun <T> ValueManager<T>.getValue(thisObj: Any?, property: KProperty<*>): T = value

public operator fun <T> ValueManager<T>.setValue(
    thisObj: Any?,
    property: KProperty<*>,
    value: T,
) {
    this.value = value
}
