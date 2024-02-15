package dev.programadorthi.state.core.validation

public interface Validator<in T> {
    public val message: (T) -> String

    public fun isValid(value: T): Boolean
}