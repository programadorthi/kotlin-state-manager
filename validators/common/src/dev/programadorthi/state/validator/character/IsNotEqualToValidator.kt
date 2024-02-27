package dev.programadorthi.state.validator.character

import dev.programadorthi.state.core.validation.Validator

public class IsNotEqualToValidator(
    override val message: (Char) -> String,
    private val other: Char,
    private val ignoreCase: Boolean = false,
) : Validator<Char> {

    override fun isValid(value: Char): Boolean =
        value.equals(other, ignoreCase = ignoreCase).not()

}