package dev.programadorthi.state.validator.comparable

import dev.programadorthi.state.core.validation.Validator

public class IsGreaterThanOrEqualToValidator<T : Comparable<T>>(
    override val message: (T) -> String,
    private val start: T,
) : Validator<T> {

    override fun isValid(value: T): Boolean = value >= start

}