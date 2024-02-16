package dev.programadorthi.state.core.extension

import dev.programadorthi.state.core.ValueManager
import dev.programadorthi.state.core.validation.Validator
import kotlin.reflect.KProperty

public operator fun <T> ValueManager<T>.setValue(
    thisRef: Any?,
    property: KProperty<*>,
    value: T
) {
    update { value }
}

public operator fun <T> ValueManager<T>.minusAssign(element: Validator<T>) {
    removeValidator(element)
}

public operator fun <T> ValueManager<T>.minusAssign(elements: Iterable<Validator<T>>) {
    elements.forEach { validator -> removeValidator(validator) }
}

public operator fun <T> ValueManager<T>.plusAssign(element: Validator<T>) {
    addValidator(element)
}

public operator fun <T> ValueManager<T>.plusAssign(elements: Iterable<Validator<T>>) {
    elements.forEach { validator -> addValidator(validator) }
}