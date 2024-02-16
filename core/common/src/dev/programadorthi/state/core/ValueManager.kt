package dev.programadorthi.state.core

import androidx.compose.runtime.State
import dev.programadorthi.state.core.action.CollectAction
import dev.programadorthi.state.core.action.UpdateAction
import dev.programadorthi.state.core.validation.Validator

@OptIn(ExperimentalStdlibApi::class)
public interface ValueManager<T> : AutoCloseable, State<T> {
    public val closed: Boolean

    public val isValid: Boolean

    public val messages: List<String>

    public fun collect(action: CollectAction<T>)

    public fun update(action: UpdateAction<T>)

    public fun addValidator(validator: Validator<T>)

    public fun removeValidator(validator: Validator<T>)
}
