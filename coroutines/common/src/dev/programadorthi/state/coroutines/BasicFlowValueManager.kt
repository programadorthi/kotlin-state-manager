package dev.programadorthi.state.coroutines

internal class BasicFlowValueManager<T>(initialValue: T) : BaseFlowValueManager<T>(initialValue) {

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