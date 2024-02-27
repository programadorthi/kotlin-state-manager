package dev.programadorthi.state.validator.string

import dev.programadorthi.state.core.validation.Validator

public class NotStartsWithValidator(
    override val message: (CharSequence) -> String,
    private val prefix: CharSequence,
    private val ignoreCase: Boolean = false,
) : Validator<CharSequence> {
    
    override fun isValid(value: CharSequence): Boolean =
        value.startsWith(prefix, ignoreCase = ignoreCase).not()

}