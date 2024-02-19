package dev.programadorthi.state.coroutines

import dev.programadorthi.state.core.validation.ValidatorManager
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class ValidatorManagerFlow<T>(
    validatorManager: ValidatorManager<T>
) : StateFlow<ValidatorResult> {

    private val stateFlow = MutableStateFlow(
        ValidatorResult(
            isValid = validatorManager.isValid,
            messages = validatorManager.messages,
        )
    )

    init {
        validatorManager.onValidated { messages ->
            stateFlow.tryEmit(
                ValidatorResult(
                    isValid = messages.isEmpty(),
                    messages = messages,
                )
            )
        }
    }

    override val replayCache: List<ValidatorResult>
        get() = stateFlow.replayCache

    override val value: ValidatorResult
        get() = stateFlow.value

    override suspend fun collect(collector: FlowCollector<ValidatorResult>): Nothing {
        stateFlow.collect(collector)
    }
}