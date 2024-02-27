package dev.programadorthi.state.validator.string

import dev.programadorthi.state.core.validation.Validator

public class RegexContainsValidator(
    override val message: (CharSequence) -> String,
    private val regex: Regex,
) : Validator<CharSequence> {

    override fun isValid(value: CharSequence): Boolean = value.contains(regex)

}