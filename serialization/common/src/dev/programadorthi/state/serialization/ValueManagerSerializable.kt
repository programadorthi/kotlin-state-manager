package dev.programadorthi.state.serialization

import dev.programadorthi.state.core.ValueManager
import kotlinx.serialization.Serializable

public typealias ValueManager<T> = @Serializable(with = ValueManagerSerializer::class) ValueManager<T>