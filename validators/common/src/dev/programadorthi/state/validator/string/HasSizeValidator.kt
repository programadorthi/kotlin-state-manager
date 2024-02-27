package dev.programadorthi.state.validator.string

import dev.programadorthi.state.core.validation.Validator

public class HasSizeValidator(
    override val message: (CharSequence) -> String,
    minSize: Long = 1,
    maxSize: Long = Long.MAX_VALUE,
) : Validator<CharSequence> {
    private val range = minSize..maxSize

    override fun isValid(value: CharSequence): Boolean = value.length in range

}
