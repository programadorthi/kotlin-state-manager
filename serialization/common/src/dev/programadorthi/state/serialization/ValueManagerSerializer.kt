package dev.programadorthi.state.serialization

import dev.programadorthi.state.core.ValueManager
import dev.programadorthi.state.core.extension.basicValueManager
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal class ValueManagerSerializer<T>(
    private val valueSerializer: KSerializer<T>
) : KSerializer<ValueManager<T>> {

    override val descriptor: SerialDescriptor = valueSerializer.descriptor

    override fun deserialize(decoder: Decoder): ValueManager<T> {
        val value = valueSerializer.deserialize(decoder)
        return basicValueManager(value)
    }

    override fun serialize(encoder: Encoder, value: ValueManager<T>) {
        valueSerializer.serialize(encoder, value.value)
    }
}