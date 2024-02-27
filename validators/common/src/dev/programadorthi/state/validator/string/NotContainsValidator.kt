package dev.programadorthi.state.validator.string

import dev.programadorthi.state.core.validation.Validator

public class NotContainsValidator(
    override val message: (CharSequence) -> String,
    private val target: CharSequence,
    private val ignoreCase: Boolean = false,
) : Validator<CharSequence> {

    override fun isValid(value: CharSequence): Boolean =
        value.contains(target, ignoreCase = ignoreCase).not()

}