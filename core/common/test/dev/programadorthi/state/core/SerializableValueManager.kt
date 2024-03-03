package dev.programadorthi.state.core

import kotlinx.serialization.Serializable

@Serializable
internal data class Color(val name: String)

@Serializable
internal data class SerializableValueManager(
    val intValue: ValueManager<Int>,
    val stringValue: ValueManager<String>,
    val color: ValueManager<Color>,
    val composite: ValueManager<ValueManager<List<Color>>>
)