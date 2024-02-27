package dev.programadorthi.state.validator.character

import dev.programadorthi.state.core.validation.Validator

public class IsDigitValidator(
    override val message: (Char) -> String
) : Validator<Char> {

    override fun isValid(value: Char): Boolean = value.isDigit()

}