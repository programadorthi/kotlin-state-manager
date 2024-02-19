package dev.programadorthi.state.compose

import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.programadorthi.state.coroutines.BaseFlowValueManager

public abstract class BaseComposeValueManager<T>(
    initialValue: T,
    private val policy: SnapshotMutationPolicy<T>,
) : BaseFlowValueManager<T>(initialValue), ComposeValueManager<T> {

    private val valid = mutableStateOf(true)
    private val localMessages = mutableStateOf(listOf(""))

    private var currentValue by mutableStateOf(initialValue, policy)

    override val isValidAsState: State<Boolean>
        get() = valid

    override val messagesAsState: State<List<String>>
        get() = localMessages

    override var value: T = initialValue
        get() = currentValue
        set(value) {
            if (policy.equivalent(field, value).not()) {
                super.value = value
                valid.value = super.isValid
                localMessages.value = super.messages
                if (super.value == value) {
                    currentValue = value
                }
            }
        }
}