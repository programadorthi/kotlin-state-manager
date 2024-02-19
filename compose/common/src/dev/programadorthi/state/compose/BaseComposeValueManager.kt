package dev.programadorthi.state.compose

import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.programadorthi.state.coroutines.BaseFlowValueManager

public abstract class BaseComposeValueManager<T>(
    initialValue: T,
    final override val policy: SnapshotMutationPolicy<T>,
) : BaseFlowValueManager<T>(initialValue), ComposeValueManager<T> {

    private var currentValue by mutableStateOf(initialValue, policy)

    override var value: T = initialValue
        get() = currentValue
        set(value) {
            if (policy.equivalent(field, value).not()) {
                super.value = value
                if (super.value == value) {
                    currentValue = value
                }
            }
        }

    override fun component1(): T = value

    override fun component2(): (T) -> Unit = { value = it }
}