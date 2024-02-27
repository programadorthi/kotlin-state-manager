package dev.programadorthi.state.validator.any

import dev.programadorthi.state.core.validation.Validator

public class IsEqualToValidator<T>(
    override val message: (T?) -> String,
    private val other: T?,
) : Validator<T?> {

    override fun isValid(value: T?): Boolean = value == other

}