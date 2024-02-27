package dev.programadorthi.state.validator.string

import dev.programadorthi.state.core.validation.Validator

public class NotInValidator(
    override val message: (String) -> String,
    private val values: Iterable<String>,
    private val ignoreCase: Boolean = false,
) : Validator<String> {

    override fun isValid(value: String): Boolean =
        values.any { other -> other.equals(value, ignoreCase = ignoreCase) }.not()

}