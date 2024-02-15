package dev.programadorthi.state.core

import dev.programadorthi.state.core.action.CollectAction
import dev.programadorthi.state.core.action.UpdateAction
import dev.programadorthi.state.core.platform.Closeable
import dev.programadorthi.state.core.validation.Validator

public interface ValueManager<T> : Closeable {
    public val closed: Boolean

    public val isValid: Boolean

    public val messages: List<String>

    public val value: T

    public fun collect(action: CollectAction<T>)

    public fun update(action: UpdateAction<T>)

    public fun addValidator(validator: Validator<T>)

    public fun removeValidator(validator: Validator<T>)
}
