package dev.programadorthi.state.compose

import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.lifecycle.SavedStateHandle
import dev.programadorthi.state.core.ValueManager
import dev.programadorthi.state.core.extension.basicValueManager
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

public fun <T> composeValueManager(
    initialValue: T,
    stateRestorationKey: String,
    savedStateHandle: SavedStateHandle,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
    saver: Saver<T, out Any> = autoSaver(),
): ValueManager<T> = ComposeValueManagerSavedStateHandle(
    valueManager = basicValueManager(initialValue),
    policy = policy,
    savedStateHandle = savedStateHandle,
    stateRestorationKey = stateRestorationKey,
    saver = saver,
)

public fun <T> composeValueManager(
    initialValue: T,
    savedStateHandle: SavedStateHandle,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
    saver: Saver<T, out Any> = autoSaver(),
): ComposeValueManager<T> = basicValueManager(initialValue).asState(
    savedStateHandle = savedStateHandle,
    policy = policy,
    saver = saver,
)

public fun <T> ValueManager<T>.asState(
    savedStateHandle: SavedStateHandle,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
    saver: Saver<T, out Any> = autoSaver(),
): ComposeValueManager<T> = PropertyDelegateProvider { _, property ->
    val state = asState(
        stateRestorationKey = property.name,
        savedStateHandle = savedStateHandle,
        policy = policy,
        saver = saver,
    )
    object : ReadWriteProperty<Any?, T> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T = state.value

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            state.value = value
        }
    }
}

public fun <T> ValueManager<T>.asState(
    stateRestorationKey: String,
    savedStateHandle: SavedStateHandle,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
    saver: Saver<T, out Any> = autoSaver(),
): ValueManager<T> = ComposeValueManagerSavedStateHandle(
    valueManager = this,
    policy = policy,
    savedStateHandle = savedStateHandle,
    stateRestorationKey = stateRestorationKey,
    saver = saver,
)