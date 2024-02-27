package dev.programadorthi.state.validator.number

import dev.programadorthi.state.core.validation.Validator

public class IsFiniteValidator(
    override val message: (Number) -> String
) : Validator<Number> {

    override fun isValid(value: Number): Boolean = value.toDouble().isFinite()

}