package dev.programadorthi.state.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.runtime.saveable.rememberSaveable
import dev.programadorthi.state.core.ValueManager

@Composable
public fun <T> rememberValueManager(
    valueManager: () -> ValueManager<T>
): ValueManager<T> = remember {
    valueManager().asState()
}

@Composable
public fun <T> rememberSaveableValueManager(
    saver: Saver<T, out Any> = autoSaver(),
    valueManager: () -> ValueManager<T>
): ValueManager<T> = rememberSaveable(
    init = valueManager,
    saver = ValueManagerSaver(
        saver = saver,
        manager = valueManager,
    ),
)
