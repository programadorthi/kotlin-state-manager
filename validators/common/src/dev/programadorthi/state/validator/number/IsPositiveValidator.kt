package dev.programadorthi.state.validator.number

import dev.programadorthi.state.core.validation.Validator

public class IsPositiveValidator(
    override val message: (Number) -> String
) : Validator<Number> {

    // TODO: Is safe cast any type to double to compare?
    // TODO: Whether yes, why there is no this kind of extension in the stdlib?
    override fun isValid(value: Number): Boolean = value.toDouble() > 0.0

}