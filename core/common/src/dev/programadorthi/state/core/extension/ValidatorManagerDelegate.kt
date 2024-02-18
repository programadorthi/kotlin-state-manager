package dev.programadorthi.state.core.extension

import dev.programadorthi.state.core.validation.Validator
import dev.programadorthi.state.core.validation.ValidatorManager

public operator fun <T> ValidatorManager<T>.minusAssign(element: Validator<T>) {
    removeValidator(element)
}

public operator fun <T> ValidatorManager<T>.minusAssign(elements: Iterable<Validator<T>>) {
    elements.forEach { validator -> removeValidator(validator) }
}

public operator fun <T> ValidatorManager<T>.plusAssign(element: Validator<T>) {
    addValidator(element)
}

public operator fun <T> ValidatorManager<T>.plusAssign(elements: Iterable<Validator<T>>) {
    elements.forEach { validator -> addValidator(validator) }
}