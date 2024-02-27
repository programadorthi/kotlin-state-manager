package dev.programadorthi.state.validator.string

import dev.programadorthi.state.core.validation.Validator

public class NotEndsWithValidator(
    override val message: (CharSequence) -> String,
    private val suffix: CharSequence,
    private val ignoreCase: Boolean = false,
) : Validator<CharSequence> {

    override fun isValid(value: CharSequence): Boolean =
        value.endsWith(suffix, ignoreCase = ignoreCase).not()

}