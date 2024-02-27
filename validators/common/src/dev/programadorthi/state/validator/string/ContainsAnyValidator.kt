package dev.programadorthi.state.validator.string

import dev.programadorthi.state.core.validation.Validator

public class ContainsAnyValidator(
    override val message: (CharSequence) -> String,
    private val targets: Iterable<CharSequence>,
    private val ignoreCase: Boolean = false,
) : Validator<CharSequence> {

    override fun isValid(value: CharSequence): Boolean =
        targets.any { target -> value.contains(target, ignoreCase = ignoreCase) }

}