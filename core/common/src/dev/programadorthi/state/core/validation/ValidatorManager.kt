package dev.programadorthi.state.core.validation

public interface ValidatorManager<T> {

    public val isValid: Boolean

    public val messages: List<String>

    public fun addValidator(validator: Validator<T>)

    public fun removeValidator(validator: Validator<T>)

    public fun validate(): Boolean
}