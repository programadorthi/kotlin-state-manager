package dev.programadorthi.core.extension

import dev.programadorthi.core.ValueManager
import kotlin.reflect.KProperty

public operator fun <T> ValueManager<T>.getValue(thisRef: Any?, property: KProperty<*>): T = value

public operator fun <T> ValueManager<T>.setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    update(value)
}
