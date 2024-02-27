package dev.programadorthi.state.validator.string

import dev.programadorthi.state.core.validation.Validator

public class IsNotEmptyValidator(
    override val message: (CharSequence?) -> String
) : Validator<CharSequence?> {

    override fun isValid(value: CharSequence?): Boolean = value.isNullOrEmpty().not()

}
