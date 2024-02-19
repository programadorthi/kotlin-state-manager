package dev.programadorthi.state.compose

import androidx.compose.runtime.SnapshotMutationPolicy

internal class BasicComposeValueManager<T>(
    initialValue: T,
    policy: SnapshotMutationPolicy<T>,
) : BaseComposeValueManager<T>(initialValue, policy) {

    internal fun notifyParentChanged(previous: T, next: T) {
        notifyChanged(previous, next)
    }

    internal fun notifyParentCollector(value: T) {
        notifyCollector(value)
    }

    internal fun notifyParentError(throwable: Throwable) {
        notifyError(throwable)
    }

    internal fun notifyParentValidator(messages: List<String>) {
        notifyValidator(messages)
    }

}