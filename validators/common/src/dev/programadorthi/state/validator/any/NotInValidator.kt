package dev.programadorthi.state.validator.any

import dev.programadorthi.state.core.validation.Validator

public class NotInValidator<T>(
    override val message: (T) -> String,
    private val values: Iterable<T>,
) : Validator<T> {

    override fun isValid(value: T): Boolean = values.contains(value).not()

}