package dev.programadorthi.state.validator.string

import dev.programadorthi.state.core.validation.Validator

public class StartsWithValidator(
    override val message: (CharSequence) -> String,
    private val prefix: CharSequence,
    private val ignoreCase: Boolean = false,
) : Validator<CharSequence> {

    override fun isValid(value: CharSequence): Boolean =
        value.startsWith(prefix, ignoreCase = ignoreCase)

}