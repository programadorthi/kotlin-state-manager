package dev.programadorthi.state.compose

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.autoSaver
import dev.programadorthi.state.core.ValueManager

internal class ValueManagerSaver<T>(
    private val saver: Saver<T, out Any> = autoSaver(),
    private val manager: () -> ValueManager<T>,
) : Saver<ValueManager<T>, Any> {

    override fun restore(value: Any): ValueManager<T> {
        @Suppress("UNCHECKED_CAST")
        (saver as Saver<T, Any>)

        val result = manager()
        saver.restore(value)?.let { other ->
            result.value = other
        }
        return result
    }

    override fun SaverScope.save(value: ValueManager<T>): Any? = with(saver) {
        save(value.value)
    }
}