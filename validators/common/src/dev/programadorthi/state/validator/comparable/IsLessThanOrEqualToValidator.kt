package dev.programadorthi.state.validator.comparable

import dev.programadorthi.state.core.validation.Validator

public class IsLessThanOrEqualToValidator<T : Comparable<T>>(
    override val message: (T) -> String,
    private val end: T,
) : Validator<T> {

    override fun isValid(value: T): Boolean = value <= end

}