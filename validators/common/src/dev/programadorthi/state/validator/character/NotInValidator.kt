package dev.programadorthi.state.validator.character

import dev.programadorthi.state.core.validation.Validator

public class NotInValidator(
    override val message: (Char) -> String,
    private val values: Iterable<Char>,
    private val ignoreCase: Boolean = false,
) : Validator<Char> {

    override fun isValid(value: Char): Boolean =
        values.any { other -> value.equals(other, ignoreCase = ignoreCase) }.not()

}