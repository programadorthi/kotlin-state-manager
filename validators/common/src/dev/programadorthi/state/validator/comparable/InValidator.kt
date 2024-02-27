package dev.programadorthi.state.validator.comparable

import dev.programadorthi.state.core.validation.Validator

public class InValidator<T : Comparable<T>>(
    override val message: (T) -> String,
    start: T,
    end: T,
) : Validator<T> {
    private val range = start..end

    override fun isValid(value: T): Boolean = value in range

}