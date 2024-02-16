package dev.programadorthi.state.validator.string

import dev.programadorthi.state.core.validation.Validator

public data class IsEqualToValidator(
    override val message: (String?) -> String,
    private val other: String,
    private val ignoreCase: Boolean = false,
) : Validator<String?> {

    override fun isValid(value: String?): Boolean =
        other.equals(value, ignoreCase = ignoreCase)

}
