package dev.programadorthi.state.core.action

public typealias ChangeAction<T> = (previous: T, next: T) -> Unit