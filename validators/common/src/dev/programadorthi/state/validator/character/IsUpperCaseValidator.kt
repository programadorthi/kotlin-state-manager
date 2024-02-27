package dev.programadorthi.state.validator.character

import dev.programadorthi.state.core.validation.Validator

public class IsUpperCaseValidator(
    override val message: (Char) -> String
) : Validator<Char> {

    override fun isValid(value: Char): Boolean = value.isUpperCase()

}