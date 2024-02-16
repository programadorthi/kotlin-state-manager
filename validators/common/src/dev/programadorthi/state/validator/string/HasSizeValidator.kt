package dev.programadorthi.state.validator.string

import dev.programadorthi.state.core.validation.Validator

public data class HasSizeValidator(
    override val message: (CharSequence) -> String,
    private val minSize: Long = 1,
    private val maxSize: Long = Long.MAX_VALUE,
) : Validator<CharSequence> {

    override fun isValid(value: CharSequence): Boolean = value.length in minSize.rangeTo(maxSize)

}
