package dev.programadorthi.state.compose

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.programadorthi.state.core.validation.ValidatorManager

public interface ValidatorManagerState : State<Boolean> {

    public operator fun component1(): Boolean

    public operator fun component2(): List<String>

}

internal class ValidatorManagerStateImpl<T>(
    validatorManager: ValidatorManager<T>
) : ValidatorManagerState {

    private var valid by mutableStateOf(validatorManager.isValid)
    private var messages by mutableStateOf(validatorManager.messages)

    init {
        validatorManager.onValidated { other ->
            valid = other.isEmpty()
            messages = other
        }
    }

    override val value: Boolean
        get() = valid

    override fun component1(): Boolean = value

    override fun component2(): List<String> = messages

}