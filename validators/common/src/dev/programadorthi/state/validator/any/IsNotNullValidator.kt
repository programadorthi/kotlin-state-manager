package dev.programadorthi.state.validator.any

import dev.programadorthi.state.core.validation.Validator

public class IsNotNullValidator<T>(
    override val message: (T) -> String
) : Validator<T> {

    override fun isValid(value: T): Boolean = value != null

}
